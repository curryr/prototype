import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGIDUser } from 'app/shared/model/gid-user.model';

type EntityResponseType = HttpResponse<IGIDUser>;
type EntityArrayResponseType = HttpResponse<IGIDUser[]>;

@Injectable({ providedIn: 'root' })
export class GIDUserService {
  public resourceUrl = SERVER_API_URL + 'api/gid-users';

  constructor(protected http: HttpClient) {}

  create(gIDUser: IGIDUser): Observable<EntityResponseType> {
    return this.http.post<IGIDUser>(this.resourceUrl, gIDUser, { observe: 'response' });
  }

  update(gIDUser: IGIDUser): Observable<EntityResponseType> {
    return this.http.put<IGIDUser>(this.resourceUrl, gIDUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGIDUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGIDUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
