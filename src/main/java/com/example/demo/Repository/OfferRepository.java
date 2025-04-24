package com.example.demo.Repository;

import com.example.demo.Model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Integer> {
    int countByInvestorIdAndOfferStatus(Integer investorId, String offerStatus);  // تعديل هذا السطر

    int countByOwnerId(int ownerId);

    int countByOwnerIdAndOfferStatus(int ownerId, String accepted);

    List<Offer> findOfferByIdAndOfferStatusNot(Integer id, String accepted);

    Offer findOfferById(Integer offerId);


    List<Offer> findByPropertyId(Integer propertyId);
    public interface offerRepository extends JpaRepository<Offer, Integer> {
        Offer findTopByInvestorIdOrderByLastOfferTimeDesc(Integer investorId);
    }
        // استعلام للحصول على آخر عرض للمستثمر
        Offer findTopByInvestorIdOrderByLastOfferTimeDesc(Integer investorId);



}