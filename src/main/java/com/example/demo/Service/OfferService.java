package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.Model.Investor;
import com.example.demo.Model.Offer;
import com.example.demo.Model.Property;
import com.example.demo.Repository.InvestorRepository;
import com.example.demo.Repository.OfferRepository;
import com.example.demo.Repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final InvestorRepository investorRepository;
    private final PropertyRepository propertyRepository;
    private final OfferRepository offerRepository;

    public List<Offer> getAllOffers(){
        return offerRepository.findAll();
    }

    public void addOfferByInvestor(Integer investorId, Integer propertyId, Offer offer) {

        Investor investor = investorRepository.findById(investorId).orElse(null);
        if (investor == null) {
            throw new ApiException("Investor not found");
        }

        // البحث عن العقار باستخدام المعرف
        Property property = propertyRepository.findPropertiesById(propertyId);
        if (property == null) {
            throw new ApiException("Property not found");
        }

        if (!property.getStatus().equals("Active")) {
            throw new ApiException("Property not active yet");
        }

        offer.setInvestor(investor);
        offer.setProperty(property);
        offer.setOfferStatus("pending");
        offer.setOrderDate(LocalDate.now());
        offerRepository.save(offer);
    }
    public void addOffer(Offer offer){


        offerRepository.save(offer);
    }



    public void updateOffer(Offer offer, Integer id){
        Offer oldOffer= offerRepository.findOfferById(id);

        if (oldOffer==null){
            throw new ApiException("Offer Not found!!");
        }

        oldOffer.setOfferStatus(offer.getOfferStatus());
        oldOffer.setInvestor(offer.getInvestor());
        oldOffer.setProperty(offer.getProperty());
        oldOffer.setOfferStatus(offer.getOfferStatus());
        oldOffer.setOrderDate(offer.getOrderDate());
        oldOffer.setProposedCost(offer.getProposedCost());
        oldOffer.setProposedYears(offer.getProposedYears());

        offerRepository.save(oldOffer);
    }

    public void deleteOffer(Integer id){
        Offer offer= offerRepository.findOfferById(id);

        if(offer == null){throw new ApiException("Offer Not found!!");}
        offerRepository.delete(offer);
    }

    public void assignInvestorToOffer(Integer offerId, Integer investorId) {
        Offer offer = offerRepository.findOfferById(offerId);
        Investor investor = investorRepository.findInvestorById(investorId);

        if (offer == null) throw new ApiException("Offer not found");
        if (investor == null) throw new ApiException("Investor not found");


        offer.setInvestor(investor);
        offerRepository.save(offer);
    }

    public void assignPropertyToOffer(Integer offerId, Integer propertyId) {
        Offer offer = offerRepository.findOfferById(offerId);
        Property property = propertyRepository.findPropertiesById(propertyId);

        if (offer == null){ throw new ApiException("Offer not found");}
        if (property == null){ throw new ApiException("Property not found");}

        offer.setProperty(property);
        offerRepository.save(offer);
    }
//--------------------------------------------------------------
    //Duja
   public Offer submitOffer(Integer propertyId, Integer investorId, Integer price) {
    Investor investor = investorRepository.findById(investorId)
            .orElseThrow(() -> new ApiException("Investor not found"));

    Property property = propertyRepository.findById(propertyId)
            .orElseThrow(() -> new ApiException("Property not found"));

    if (!property.isAcceptingOffers()) {
        throw new ApiException("This property is not accepting offers at the moment.");
    }

    Offer lastOffer = offerRepository.findTopByInvestorIdOrderByLastOfferTimeDesc(investorId);
    if (lastOffer != null) {
        long minutesDiff = ChronoUnit.MINUTES.between(lastOffer.getLastOfferTime(), LocalDateTime.now());
        if (minutesDiff < 1) {
            throw new ApiException("You cannot submit more than one offer per minute.");
        }
    }

    Offer newOffer = new Offer();
    newOffer.setProposedCost(price);
    newOffer.setProperty(property);
    newOffer.setInvestor(investor);
    newOffer.setLastOfferTime(LocalDateTime.now());

    return offerRepository.save(newOffer);
}
}




