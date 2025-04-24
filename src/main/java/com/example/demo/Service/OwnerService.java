package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.Model.Owner;
import com.example.demo.Repository.OfferRepository;
import com.example.demo.Repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OfferRepository offerRepository;

    public void addOwner(Owner owner){
        if (ownerRepository.existsByEmail(owner.getEmail())) {
            throw new ApiException("This email already exists in the system");
        }
        // إذا لم يكن موجودًا، احفظ البيانات
        ownerRepository.save(owner);
    }

    public List<Owner> getAllOwners(){
        return ownerRepository.findAll();
    }

    public void updateOwner(Owner owner,Integer id){
        Owner oldeOwner=ownerRepository.findOwnerById(id);
        if (oldeOwner==null){
            throw new ApiException("owner not found");
        }
        oldeOwner.setName(owner.getName());
        oldeOwner.setEmail(owner.getEmail());
        oldeOwner.setPassword(owner.getPassword());
        oldeOwner.setPhone_number(owner.getPhone_number());

        ownerRepository.save(oldeOwner);
    }

    public void deleteOwner(Integer id){
        Owner oldOwner= ownerRepository.findOwnerById(id);
        if (oldOwner==null){
            throw new ApiException("Owner not found");
        }
        ownerRepository.delete(oldOwner);
    }

    //----------------------------------------------------------------
    //Duja
    public double calculateOfferAcceptanceRate(int ownerId) {

        int totalOffers = offerRepository.countByOwnerId(ownerId);

        if (totalOffers == 0) {
            return 0.0;
        }


        int acceptedOffers = offerRepository.countByOwnerIdAndOfferStatus(ownerId, "Accepted");

        return (double) acceptedOffers / totalOffers * 100;
    }


}
