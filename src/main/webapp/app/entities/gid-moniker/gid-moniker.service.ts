import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGIDMoniker } from 'app/shared/model/gid-moniker.model';

type EntityResponseType = HttpResponse<IGIDMoniker>;
type EntityArrayResponseType = HttpResponse<IGIDMoniker[]>;

@Injectable({ providedIn: 'root' })
export class GIDMonikerService {
  public resourceUrl = SERVER_API_URL + 'api/gid-monikers';

  constructor(protected http: HttpClient) {}

  create(gIDMoniker: IGIDMoniker): Observable<EntityResponseType> {
    return this.http.post<IGIDMoniker>(this.resourceUrl, gIDMoniker, { observe: 'response' });
  }

  update(gIDMoniker: IGIDMoniker): Observable<EntityResponseType> {
    return this.http.put<IGIDMoniker>(this.resourceUrl, gIDMoniker, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGIDMoniker>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGIDMoniker[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
