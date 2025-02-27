import { HttpInterceptorFn } from '@angular/common/http';

export const httpRequestInterceptor: HttpInterceptorFn = (req, next) => {
  const modifiedReq = req.clone({
    setHeaders: { Accept: 'application/json' },
  });
  return next(modifiedReq);
};
