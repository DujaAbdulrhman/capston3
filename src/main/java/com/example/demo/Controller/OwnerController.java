package com.example.demo.Controller;

import com.example.demo.API.ApiResponse;
import com.example.demo.Model.Owner;
import com.example.demo.Service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/owner")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/add")
    public ResponseEntity add(@Valid @RequestBody Owner owner){
        ownerService.addOwner(owner);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(ownerService.getAllOwners());
    }

    @PutMapping("update/{id}")
    public ResponseEntity update(@RequestBody Owner owner,@PathVariable Integer id){
        ownerService.updateOwner(owner,id);
        return ResponseEntity.status(200).body("updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        ownerService.deleteOwner(id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }
//------------------------------------------------------------------


    //6  تحسب معدل قبول المالك Duja
   @GetMapping("/{ownerId}/offer-acceptance-rate")
    public ResponseEntity<String> getOfferAcceptanceRate(@PathVariable Integer ownerId) {
        String result = ownerService.calculateOfferAcceptanceRate(ownerId);
        return ResponseEntity.ok(result);
    }


}
