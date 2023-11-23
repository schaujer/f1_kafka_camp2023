import {ApplicationConfig, importProvidersFrom} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {NgxEchartsModule} from "ngx-echarts";
import {provideAnimations} from '@angular/platform-browser/animations';
import {HttpClientModule} from "@angular/common/http";

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),
    importProvidersFrom(NgxEchartsModule.forRoot({
        echarts: () => import('echarts'),
    })), provideAnimations(), importProvidersFrom(HttpClientModule)]
};
