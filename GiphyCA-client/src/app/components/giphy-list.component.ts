import { Component,Input, OnInit } from '@angular/core';
import { GiphyService } from '../giphy.service';
import {NgForm} from '@angular/forms';
import {Giphy} from '../Giphy';

@Component({
  selector: 'app-giphy-list',
  templateUrl: './giphy-list.component.html',
  styleUrls: ['./giphy-list.component.css']
})
export class GiphyListComponent implements OnInit {


  giphys: Giphy[] = [];
  userID: string;
  @Input()
  model: Giphy;
  noOfResult:any;
  p:any;

  constructor(private giphySvc: GiphyService) { }

  ngOnInit() {
    this.noOfResult =0;


  }
  
  

  searchGiphy(form: NgForm) {
    

    this.userID = form.value.username; 
    this.giphys = []; 
  
   
    this.giphySvc.searchGiphy(form.value.searchString)
      .then((result) => {
        for (let i = 0; i < result['data'].length; i++) {
          const model:Giphy= {
            userid :this.userID,
            giphypath: result['data'][i]['images']['downsized']['url'],
            saved:false
           
          };
          this.giphys.push(model);
          this.noOfResult = this.giphys.length;
        }
      }).catch ( error => {
        console.log('error: ', error);
      });
      console.log(this.giphys);
     
     
  }

  saveGiphy(gip: Giphy) {

    const data: Giphy = {
      userid: this.userID,
      giphypath: gip.giphypath,
      saved:true
    };
    console.log(data);

    this.giphySvc.saveGiphy(data).then(data => 
      console.log(data)).catch ( error => {
        console.log('error: ', error);
      });
    
  }

  retrieveSavedGiphy(form: NgForm) {

    this.giphys = [];

    this.giphySvc.getSavedGiphy(form.value.username)
      .then((data) => {
        for (let i = 0; i < data.length; i++) {
          const model: Giphy = {
            userid: form.value.username,
            giphypath: data[i]['giphypath'],
            saved :true
           
          };
          this.giphys.push(model);
          this.noOfResult = this.giphys.length;
        }
      }).catch ( error => {
        console.log('error: ', error);
      });;
      console.log(this.giphys);
  }

  

   
}
