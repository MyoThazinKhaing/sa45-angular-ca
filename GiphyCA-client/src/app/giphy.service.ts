import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

import 'rxjs/add/operator/take';
import 'rxjs/add/operator/toPromise';
import { Giphy } from './Giphy';

@Injectable()
export class GiphyService {

  constructor(private http: HttpClient) { }


  getSavedGiphy(userid: String): Promise<any> {
    return (this.http.get('http://localhost:8080/Giphy-server/retrieve/'+userid)
      .take(1)
      .toPromise());
  }

  searchGiphy(searchString: string): Promise<any> {
    const queryString = new HttpParams().set('q', searchString);

    return (this.http.get('https://api.giphy.com/v1/gifs/search?api_key=ZkeIyQCalsmpZQeZIDRQMXgukL3OX5I7',
      {params: queryString}).toPromise());
  }

  saveGiphy (data: Giphy): Promise<any> {

    const queryString = new HttpParams()
      .set('userid', data.userid)
      .set('giphypath', data.giphypath);

    return (this.http.get('http://localhost:8080/Giphy-server/save',
      {params: queryString}).toPromise());
  }

}
