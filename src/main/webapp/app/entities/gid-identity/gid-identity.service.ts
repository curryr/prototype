import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGIDIdentity } from 'app/shared/model/gid-identity.model';

type EntityResponseType = HttpResponse<IGIDIdentity>;
type EntityArrayResponseType = HttpResponse<IGIDIdentity[]>;

@Injectable({ providedIn: 'root' })
export class GIDIdentityService {
  public resourceUrl = SERVER_API_URL + 'api/gid-identities';

  constructor(protected http: HttpClient) {}

  create(gIDIdentity: IGIDIdentity): Observable<EntityResponseType> {
    return this.http.post<IGIDIdentity>(this.resourceUrl, gIDIdentity, { observe: 'response' });
  }

  update(gIDIdentity: IGIDIdentity): Observable<EntityResponseType> {
    return this.http.put<IGIDIdentity>(this.resourceUrl, gIDIdentity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGIDIdentity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGIDIdentity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
