package com.example.demo.Controller;

import com.example.demo.API.ApiResponse;
import com.example.demo.Model.Offer;
import com.example.demo.Repository.OfferRepository;
import com.example.demo.Service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/offer")
public class OfferController {
    private final OfferService offerService;
    private final OfferRepository offerRepository;

    /*@GetMapping("/get")
    public ResponseEntity getAllOffers() {
        return ResponseEntity.status(200).body(offerService.getAllOffers());
    }

    @PostMapping("/add")
    public ResponseEntity addOffer(@Valid @RequestBody Offer offer) {
        offerService.addOffer(offer);
        return ResponseEntity.status(200).body(new ApiResponse("Offer added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateOffer(@PathVariable Integer id, @Valid @RequestBody Offer offer) {
        offerService.updateOffer(offer, id);
        return ResponseEntity.status(200).body(new ApiResponse("Offer updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOffer(@PathVariable Integer id) {
        offerService.deleteOffer(id);
        return ResponseEntity.status(200).body(new ApiResponse("Offer deleted successfully"));
    }*/

    @GetMapping("/get")
    public ResponseEntity getAllOffers() {
        return ResponseEntity.status(200).body(offerService.getAllOffers());
    }

    @PostMapping("/addOfferByInvestor/{investorId}/{propertyId}")
    public ResponseEntity addOffer(@PathVariable Integer investorId,@PathVariable Integer propertyId,@Valid @RequestBody Offer offer) {
        offerService.addOfferByInvestor(investorId,propertyId, offer);
        return ResponseEntity.status(200).body(new ApiResponse("Offer posted successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateOffer(@PathVariable Integer id, @Valid @RequestBody Offer offer) {
        offerService.updateOffer(offer, id);
        return ResponseEntity.status(200).body(new ApiResponse("Offer updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOffer(@PathVariable Integer id) {
        offerService.deleteOffer(id);
        return ResponseEntity.status(200).body(new ApiResponse("Offer deleted successfully"));
    }
//------------------------------------------

    //7 Duja مانخلي التاجر يسوي سبميت لاكثر من اوفر بنفس الدقيقه
    @PostMapping("/submit/{propertyId}/{investorId}/{price}")
    public ResponseEntity<Offer> submitOffer(@PathVariable Integer propertyId, @PathVariable Integer investorId, @PathVariable Integer price) {

        Offer newOffer = offerService.submitOffer(propertyId, investorId, price);
        return ResponseEntity.ok(newOffer);
    }



}
