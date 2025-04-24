package com.example.demo.Controller;

import com.example.demo.API.ApiException;
import com.example.demo.API.ApiResponse;
import com.example.demo.Model.Investor;
import com.example.demo.Model.Owner;
import com.example.demo.Model.Property;
import com.example.demo.Service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/property")
@RestController
@RequiredArgsConstructor
public class PropertyController {


    private final PropertyService propertyService;

    @GetMapping("/get")
    public ResponseEntity getAllProperty(){
        return ResponseEntity.status(200).body(propertyService.getAllProperties());
    }

    @PostMapping("/add")
    public ResponseEntity addProperty(@Valid @RequestBody Property property){
        propertyService.addProperty(property);
        return ResponseEntity.status(200).body(new ApiResponse("Property added successfully"));
    }






    @PutMapping("/update/{id}")
    public ResponseEntity updateProperty(@PathVariable Integer id, @Valid @RequestBody Property property){
        propertyService.updateProperty(id, property);
        return ResponseEntity.status(200).body(new ApiResponse("Property updated successfully"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProperty(@PathVariable Integer id){
        propertyService.deleteProperty(id);
        return ResponseEntity.status(200).body(new ApiResponse("Property deleted successfully"));
    }

    //----------------------------------------------------------------

    //5
    @GetMapping("/calculate-price/{propertyId}")
    public ResponseEntity<Double> calculatePrice(@PathVariable Integer propertyId) {
        // استدعاء دالة الخدمة لحساب السعر
        double price = propertyService.calculatePropertyPrice(propertyId);


        return ResponseEntity.status(200).body(price);
    }

    //4 Duja مجموع ربحه السنوي
    @GetMapping("/owner/{ownerId}/total-annual-profit")
    public String getTotalAnnualProfit(@PathVariable Integer ownerId) {
        Owner owner = new Owner();
        owner.setId(ownerId);

        return propertyService.calculateTotalAnnualProfitFromAllProperties(owner);
    }

    //3 Duja تعرض العقارات الي بتنتهي خلال فتره (هو يدخل الرقم)
    @GetMapping("/ending/{proposedYears}") //بيرجع العقارات الي بينتهي عقدها خلال سنه
    public ResponseEntity endingSoon(@PathVariable int proposedYears) {
        int proposedDays = proposedYears * 365;
        List<Property> properties = propertyService.endingSoon(proposedDays);

        if (properties.isEmpty()) {
            return ResponseEntity.status(400).body("there is nothing end by this period");
        }
        return ResponseEntity.status(200).body(properties);
    }


    //عشان يوقف استقبال العروض2 Duja
    @PutMapping("/property/{propertyId}/stop-receiving-offers")
    public ResponseEntity stopReceivingOffers(@PathVariable Integer propertyId) {
        boolean isStopped = propertyService.stopReceivingOffers(propertyId);
        if (isStopped) {
            return ResponseEntity.status(200).body("Property is no longer accepting offers.");
        } else {
            throw new ApiException("property not found");
        }
    }
    //1 duja تحسب متوسط السعر لكل العروض
    @GetMapping("/property/{propertyId}/average-offer-price")
    public ResponseEntity<Double> getAverageOfferPrice(@PathVariable Integer propertyId) {

        Property property = propertyService.getPropertyById(propertyId);
        if (property == null) {
            throw new ApiException("property not exist");
        }
        double averagePrice = propertyService.calculateAverageOfferPrice(propertyId);

        return ResponseEntity.status(200).body(averagePrice);
    }


   /* @PutMapping("/activeProperty/{propertyId}/{adminId}")
    public ResponseEntity activeTheProperty(@PathVariable Integer propertyId, @PathVariable Integer adminId){
        propertyService.activeTheProperty(propertyId, adminId);
        return ResponseEntity.status(200).body(new ApiResponse("Property is now active and visible to investors"));
    }

    @PutMapping("/rejectProperty/{propertyId}/{adminId}")
    public ResponseEntity rejectTheProperty(@PathVariable Integer propertyId, @PathVariable Integer adminId) {
        propertyService.rejectTheProperty(propertyId, adminId);
        return ResponseEntity.status(200).body(new ApiResponse("Property is now active and visible to investorsProperty has been rejected and hidden from investors"));

    }*/







}
