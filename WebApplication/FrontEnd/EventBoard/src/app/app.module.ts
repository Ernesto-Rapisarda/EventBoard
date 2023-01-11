import {LOCALE_ID, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AboutComponent } from './components/about/about.component';
import { CardComponent } from './components/card/card.component';
import { CreateEventComponent } from './components/create-event/create-event.component';
import { EventComponent } from './components/event/event.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginComponent } from './components/login/login.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { SearchComponent } from './components/search/search.component';
import { TopBarComponent } from './components/top-bar/top-bar.component';
import { RegisterComponent } from './components/register/register.component';
import {ReactiveFormsModule} from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatCardModule} from "@angular/material/card";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatListModule} from "@angular/material/list";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatRadioModule} from "@angular/material/radio";
import {HttpClientModule} from "@angular/common/http";
import { BannerJoinComponent } from './components/banner-join/banner-join.component';
import { ProfileComponent } from './components/profile/profile.component';
import {MatMenuModule} from "@angular/material/menu";
import {MatDialogModule} from "@angular/material/dialog";
import { ProfileEditDialogComponent } from './components/profile-edit-dialog/profile-edit-dialog.component';
import { DatepickerComponent } from './components/datepicker/datepicker.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import { NgxMatDatetimePickerModule,  NgxMatNativeDateModule, NgxMatTimepickerModule } from "@angular-material-components/datetime-picker";
import {DatePipe, registerLocaleData} from "@angular/common";
import { MatTabsModule } from "@angular/material/tabs";
import localeIt from '@angular/common/locales/it';
import { CommentComponent } from './components/comment/comment.component';
import { ReviewComponent } from './components/review/review.component';
import {MatBadgeModule} from "@angular/material/badge";


registerLocaleData(localeIt, 'it');

@NgModule({
  declarations: [
    AppComponent,
    AboutComponent,
    CardComponent,
    CreateEventComponent,
    EventComponent,
    HomepageComponent,
    LoginComponent,
    NotFoundComponent,
    SearchComponent,
    TopBarComponent,
    RegisterComponent,
    BannerJoinComponent,
    ProfileComponent,
    ProfileEditDialogComponent,
    DatepickerComponent,
    CommentComponent,
    ReviewComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatInputModule,
    MatIconModule,
    MatListModule,
    MatProgressBarModule,
    MatButtonModule,
    MatToolbarModule,
    MatRadioModule,
    HttpClientModule,
    MatMenuModule,
    MatDialogModule,
    MatDatepickerModule,
    MatNativeDateModule,
    NgxMatDatetimePickerModule,
    NgxMatTimepickerModule,
    NgxMatNativeDateModule,
    MatTabsModule,
    MatBadgeModule,
  ],
  providers: [
    {provide: LOCALE_ID, useValue: "it-IT"},
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
