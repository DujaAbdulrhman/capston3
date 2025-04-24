package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.Model.Contract;
import com.example.demo.Model.Offer;
import com.example.demo.Model.Owner;
import com.example.demo.Repository.ContractRepository;
import com.example.demo.Repository.OfferRepository;
import com.example.demo.Repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final OwnerRepository ownerRepository;
    private final OfferRepository offerRepository;

    public void addContract(Integer ownerId,Integer offerId,Contract contract) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("owner with id " + ownerId + " not found");
        }
        Offer offer=offerRepository.findOfferById(offerId);
        if (offer==null) {
            throw new ApiException("offer with id " + offerId + " not found");
        }
        contractRepository.save(contract);

    }
    public List getContract () {
        return contractRepository.findAll();
    }

    public void updateContract(Integer contractId, Contract contract ) {
        Contract c = (Contract) contractRepository.findContractByContractId(contractId);
        if (c == null) {
            throw new ApiException("contract with id " + contractId + " not found");
        }
        c.setContractDocumentationPath(contract.getContractDocumentationPath());
        c.setAgreeCost(contract.getAgreeCost());
        c.setEndDate(contract.getEndDate());
        c.setPaymentDate(contract.getPaymentDate());
        c.setStartDate(contract.getStartDate());
        c.setContractDate(contract.getContractDate());
        c.setUsingYears(contract.getUsingYears());
        contractRepository.save(c);
    }

    public void deleteContract(Integer contractId) {
        Contract contract = (Contract) contractRepository.findContractByContractId(contractId);
        if(contract == null){
            throw new ApiException("Course with id " + contractId + " not found");
        }
        contractRepository.delete(contract);


    }
}
