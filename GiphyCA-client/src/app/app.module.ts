import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import {NgxPaginationModule} from 'ngx-pagination'

import { AppComponent } from './app.component';
import { GiphyListComponent } from './components/giphy-list.component';
import { GiphyService } from './giphy.service';


@NgModule({
  declarations: [
    AppComponent,
    GiphyListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxPaginationModule
  ],
  providers: [ GiphyService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
