package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.Model.*;
import com.example.demo.Repository.AdminRepository;
import com.example.demo.Repository.OfferRepository;
import com.example.demo.Repository.OwnerRepository;
import com.example.demo.Repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final OwnerRepository ownerRepository;
    private  final PropertyRepository propertyRepository;
    private OfferRepository offerRepository;
    private AdminRepository adminRepository;

    public List<Property> getAllProperties(){
        return propertyRepository.findAll();
    }

    public void addProperty(Property property){
        Owner owner = ownerRepository.findOwnerById(property.getId());
        if(owner == null){
            throw new ApiException("owner required to add property");
        }

        propertyRepository.save(property);
    }


    public void updateProperty(Integer id, Property property){
        Property oldProperty = propertyRepository.findPropertiesById(id);
        if(oldProperty == null){
            throw new ApiException("Property not found");
        }
        oldProperty.setAreaSize(property.getAreaSize());
        oldProperty.setDescription(property.getDescription());
        oldProperty.setLocation(property.getLocation());
        oldProperty.setType(property.getType());
        oldProperty.setTitle(property.getTitle());
        oldProperty.setSerialNumber(property.getSerialNumber());

        propertyRepository.save(oldProperty);
    }



    public void deleteProperty(Integer id){
        Property property = propertyRepository.findPropertiesById(id);
        if(property == null){
            throw new ApiException("Property not found");
        }

        propertyRepository.delete(property);
    }






    //3 Duja
    public String calculateTotalAnnualProfitFromAllProperties(Owner owner) {
        List<Property> properties = propertyRepository.findByOwner(owner);
        if (properties.isEmpty()) {
            throw  new ApiException("There are no properties owned by this owner or the owner is not present");
        }
        double totalAnnualProfit = 0;

        for (Property property : properties) {
            Double annualRent = property.getAnnualRent();

            if (annualRent != null) {
                totalAnnualProfit += annualRent;
            }
        }

        return "Total owner's profit from all properties during the year: " + totalAnnualProfit + "SR";
    }



    //4 Duja
    public boolean stopReceivingOffers(Integer propertyId) {
        return propertyRepository.findById(propertyId).map(property -> {
            property.setAcceptingOffers(false);
            propertyRepository.save(property);
            return true;
        }).orElse(false);
    }
    

    public void rejectTheProperty(Integer propertyId,Integer adminId){
        Admin admin = adminRepository.findAdminById(adminId);
        Property property =propertyRepository.findPropertiesById(propertyId);
        if(property == null){
            throw new ApiException("Property not found");
        }
        if(admin == null){
            throw new ApiException("Admin not found");
        }
           if(property.getStatus().equals("Active")){
               throw new ApiException("Property already active");
           }
        property.setStatus("Inactive");
        propertyRepository.save(property);
    }

    //Duja
    public List<Property> endingSoon(int proposedYears) {
        int proposedDays = proposedYears * 365;
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(proposedDays);
        return propertyRepository.findPropertiesByLeaseEndDateBetween(today, endDate);
    }

    public Property findById(Integer propertyId) {
        return propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ApiException("Property not found"));
    }



    //Duja
    public double calculatePropertyPrice(Integer propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ApiException("Property not found"));

        double pricePerMeter = 1975.0;
        Double area = property.getAreaSize();

        if (area == null || area <= 0) {
            throw new ApiException("Invalid area size");
        }
        return area * pricePerMeter;
    }



    //6 بيحسب  متوسط العروض الي جته Duja
   public double calculateAverageOfferPrice(Integer propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ApiException("Property not found"));

        List<Offer> offers = offerRepository.findByPropertyId(propertyId);

        if (offers.isEmpty()) {
            return 0;
        }

        double totalPrice = 0;
        int validOffersCount = 0;

        for (Offer offer : offers) {
            Double cost = Double.valueOf(offer.getProposedCost());
            if (cost != null) {
                totalPrice += cost;
                validOffersCount++;
            }
        }

        return validOffersCount == 0 ? 0 : totalPrice / validOffersCount;
    }


    //حقت واحد حسبه متوسسط السعر لكل العروض Duja
     public Property getPropertyById(Integer propertyId) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        return property.orElse(null);
    }

}





