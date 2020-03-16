import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PrototypeSharedModule } from 'app/shared/shared.module';
import { PrototypeCoreModule } from 'app/core/core.module';
import { PrototypeAppRoutingModule } from './app-routing.module';
import { PrototypeHomeModule } from './home/home.module';
import { PrototypeEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    PrototypeSharedModule,
    PrototypeCoreModule,
    PrototypeHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PrototypeEntityModule,
    PrototypeAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class PrototypeAppModule {}
