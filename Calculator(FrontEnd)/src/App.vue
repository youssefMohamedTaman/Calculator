<template>

  <div class="calculator">

    <div class="item , op">{{output }}</div>
    <div class="item , op">{{currentValue }}</div>
  <div class='item group1' @click=" retrieveSingle('mod')">%</div>
  <div class = 'item group1' @click="partialReset">CE</div>
  <div class = 'item group1' @click="fullReset">C</div>
  <div class = 'item group1' @click="back">&#9166;</div>
  <div class = 'item group1' @click=" retrieveSingle('inverse') ">1/x</div>
  <div class = 'item group1' @click=" retrieveSingle('square')  ">x²</div>
  <div class = 'item group1' @click="  retrieveSingle('root') ;">√</div>
  <div class = 'item group1' @click="division">÷</div>
  <div class = 'item group2' @click="append(7)">7</div>
  <div class = 'item group2' @click="append(8)">8</div>
  <div class = 'item group2' @click="append(9)">9</div>
  <div class = 'item group1' @click="times">×</div>
  <div class = 'item group2' @click="append(4)">4</div>
  <div class = 'item group2' @click="append(5)">5</div>
  <div class = 'item group2' @click="append(6)">6</div>
  <div class = 'item group1' @click="subtraction">-</div>
  <div class = 'item group2' @click="append(1)">1</div>
  <div class = 'item group2' @click="append(2)">2</div>
  <div class = 'item group2' @click="append(3)">3</div>
  <div class = 'item group1' @click="addition">+</div>
  <div class = 'item group2' @click="toggle">+/-</div>
  <div class = 'item group2' @click="append(0)">0</div>
  <div class = 'item group2' @click="dot">.</div>
  <div class = 'item equal' @click="equal">=</div>
    <div class="item back-end" @click="retrieve">Calculate in backend</div>
  </div>
  <div class=""><p> previous = {{prevValue}} </p>
  <p> current = {{currentValue}} </p> <p>operator = {{operator}} </p>
  </div>

</template>

<script>


import {h} from "vue";

export default {
  name: 'App',

  data() {
    return {

      currentValue: "",
      prevValue: "",
      operator: "",
      output: "",
      calculated: false,


    }
  },

  methods: {
    fullReset() {
      this.currentValue = '';
      this.prevValue = '';
      this.operator = '';
      this.output = "";
    },
    partialReset() {
      this.currentValue = '';
    },
    toggle() {

      if (this.currentValue === '') {
        this.currentValue = "-";
      } else if (this.currentValue.charAt(0) === '-') {
        this.currentValue = this.currentValue.slice(1);
      } else {
        this.currentValue = `${-this.currentValue}`;
      }
    },
    percent() {

      this.currentValue = `${parseFloat(this.currentValue) / 100}`;
    },
    append(number) {


      if (this.currentValue === 'Error') {
        this.fullReset()
      }

      this.currentValue = this.currentValue + number;

    },
    dot() {
      let dotIndex = this.currentValue.indexOf('.');
      if (dotIndex === -1) {
        this.append('.');
      }
    },
    back() {
      if (this.currentValue === "Error") {
        this.partialReset();
      }
      this.currentValue = this.currentValue.slice(0, -1);
    },
    makeANewNum() {
      this.prevValue = parseFloat(this.currentValue);
      this.currentValue = '';
    },
    addition() {
      if (this.operator !== null) {
        this.equal() ;
      }
      this.makeANewNum();
      this.operator = '+';

    },
    division() {
      if (this.operator !== null) {
        this.equal() ;
      }
      this.makeANewNum();
      this.operator = '/';
    },
    times() {
      if (this.operator !== null) {
        this.equal() ;
      }
      this.makeANewNum();
      this.operator = '*';
    },
    subtraction() {
      if (this.operator !== null) {
        this.equal() ;
      }
      this.makeANewNum();
      this.operator = '-';
    },
    equal() {
      let x = parseFloat(this.currentValue);
      let result = 0;
      switch (this.operator) {
        case '+' :
          try {
            result = x + this.prevValue;
            this.currentValue = `${result}`;
            if(isNaN(this.currentValue)){
              throw new Error();
            }
          }
          catch(err){
            this.currentValue = "Error";
          }
          this.operator = "";
          this.calculated = true;
          break;
        case '-' :
          try{
          result = this.prevValue - x;
          this.currentValue = `${result}`;
          if(isNaN(this.currentValue)){
            throw new Error();
          }
      }
    catch(err){
      this.currentValue = "Error";
      }
          this.operator = "";
          this.calculated = true;
          break;
        case '*' :
          try{
          result = x * this.prevValue;
          this.currentValue = `${result}`;
          if(isNaN(this.currentValue)){
            throw new Error();
          }
      }
    catch(err){
      this.currentValue = "Error";
      }
          this.operator = "";
          this.calculated = true;
          break;
        case '/' :
          try{
          if (x !== 0) {
            result = this.prevValue / x;
            this.currentValue = `${result}`;
          } else {
            this.currentValue = 'Error';
          }
          if(isNaN(this.currentValue)){
            throw new Error();
          }
      }
    catch(err){
      this.currentValue = "Error";
      }
          this.operator = "";
          this.calculated = true;
          break;
      }
    },


    async  retrieve(){
      const endpoint = new URL(`http://localhost:8080/service`) ;
      endpoint.searchParams.set('op1', `${this.prevValue}`);
      endpoint.searchParams.set('op2', `${this.currentValue}`);
      endpoint.searchParams.set('operator', `${this.operator}`);
      const header = {
        'Content-Type': 'application/json',
      }
      const requestOptions = {
        method: 'GET',
        headers: header,

      }
      try {
        const response = await fetch(endpoint, requestOptions);
        if (!response.ok) {
          throw new Error();
        }
        const data = await response.json();
        console.log(data);
      } catch (err){
        console.log("Back-End Error happened");
      }
    },
    async retrieveSingle(operation) {
      const endpoint = new URL(`http://localhost:8080/service/special`);
      endpoint.searchParams.set('op', `${this.currentValue}`);
      endpoint.searchParams.set('operator', `${operation}`);
      const header  ={
        'Content-Type': 'application/json',
      }
      const requestOptions ={
        method: 'GET',
        headers: header,
      }

      try {
        const response = await fetch(endpoint, requestOptions);
        if (!response.ok) {
          throw new Error();
        }
        const data = await response.json();
        console.log(data);
       this.currentValue = data.toString();
      } catch (err){
        console.log("Back-End Error happened");
        this.currentValue = "Error";
      }

  },
  },
}




</script>
<style >
.calculator {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-gap: 2px; /* Add some gap between grid items for better visibility */
  max-width: 300px;
  margin: 0 auto;
}

.item {
  padding: 10px;
  text-align: center;
  font-size: 18px;
  border: 1px solid #ccc;
  cursor: pointer;
}

.op {
  grid-column: 1/5;
  grid-row: 1/50; /* Adjusted to cover only one row */
  background-color: whitesmoke;
  font-size: 40px;
  text-align: right;
  overflow: scroll; /* Hide content that overflows the box */
  white-space: nowrap; /* Prevent text from wrapping */
}
.back-end{
  grid-column:1/5;
}
.group1 {
  background-color: ghostwhite;

}
.equal {
  background-color: lightblue;
}
</style>