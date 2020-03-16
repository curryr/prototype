import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGIDMonikerSet } from 'app/shared/model/gid-moniker-set.model';

type EntityResponseType = HttpResponse<IGIDMonikerSet>;
type EntityArrayResponseType = HttpResponse<IGIDMonikerSet[]>;

@Injectable({ providedIn: 'root' })
export class GIDMonikerSetService {
  public resourceUrl = SERVER_API_URL + 'api/gid-moniker-sets';

  constructor(protected http: HttpClient) {}

  create(gIDMonikerSet: IGIDMonikerSet): Observable<EntityResponseType> {
    return this.http.post<IGIDMonikerSet>(this.resourceUrl, gIDMonikerSet, { observe: 'response' });
  }

  update(gIDMonikerSet: IGIDMonikerSet): Observable<EntityResponseType> {
    return this.http.put<IGIDMonikerSet>(this.resourceUrl, gIDMonikerSet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGIDMonikerSet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGIDMonikerSet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
