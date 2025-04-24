package com.example.demo.DTO;

import com.example.demo.Model.Offer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestOffersDTO {
    private Offer highestCostOffer;
    private Offer shortestYearsOffer;
}
