package com.alexu.calculator.ui.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.alexu.calculator.ui.model.Operation.makeOperation;
import static com.alexu.calculator.ui.model.Operation.makeSinglyOperation;

@CrossOrigin
@RestController
@RequestMapping(path = "service")
public class RestAPI {

    @GetMapping
    public ResponseEntity<Double> operation(@RequestParam(value="op1", defaultValue ="0") double op1  , @RequestParam(value="op2" , defaultValue ="0")double op2, @RequestParam(value="operator" , defaultValue ="0") char operator) throws Exception {
        try {
            double result = makeOperation(op1, op2, operator);
            return(new ResponseEntity<>(result ,HttpStatus.OK))  ;
        } catch (Exception e) {
            return (new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }
    }
    @CrossOrigin
    @GetMapping(path = "special")
    public ResponseEntity<Double> singleOperation(@RequestParam(value = "op") double op, @RequestParam(value = "operator") String operator) throws  Exception{
        try {
            double result = makeSinglyOperation(op, operator);
            return (new ResponseEntity<>(result, HttpStatus.OK));
        } catch (Exception e) {
            return (new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }
    }

}





