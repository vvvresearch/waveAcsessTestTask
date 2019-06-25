import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPresentation } from 'app/shared/model/presentation.model';

type EntityResponseType = HttpResponse<IPresentation>;
type EntityArrayResponseType = HttpResponse<IPresentation[]>;

@Injectable({ providedIn: 'root' })
export class PresentationService {
  public resourceUrl = SERVER_API_URL + 'api/presentations';

  constructor(protected http: HttpClient) {}

  create(presentation: IPresentation): Observable<EntityResponseType> {
    return this.http.post<IPresentation>(this.resourceUrl, presentation, { observe: 'response' });
  }

  update(presentation: IPresentation): Observable<EntityResponseType> {
    return this.http.put<IPresentation>(this.resourceUrl, presentation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPresentation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPresentation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
