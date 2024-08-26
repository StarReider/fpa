package org.robe.fpa.controller;

import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.Account;
import org.robe.fpa.model.Currency;
import org.robe.fpa.service.impl.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    
    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        List<Currency> currencies = currencyService.getAllCurrencies();
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Currency> getCurrencyByCode(@PathVariable("code") String currencyCode) {
        Optional<Currency> currencyOptional = currencyService.getCurrencyByCode(currencyCode);
        return currencyOptional.map(ResponseEntity::ok)
                               .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency) {
        currencyService.createCurrency(currency);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable("code") String currencyCode) {
        currencyService.deleteCurrency(currencyCode);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/sync")
    public ResponseEntity<Void> sync() {
        currencyService.syncCurrencies();
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{code}")
    public ResponseEntity<Account> updateAccount(@PathVariable("code") String currencyCode,
                                                 @RequestBody Currency currencyDetails) {
        boolean updated = currencyService.updateCurrency(currencyCode, currencyDetails);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
