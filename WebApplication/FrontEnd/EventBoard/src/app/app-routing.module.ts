import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {HomepageComponent} from "./components/homepage/homepage.component";
import {CreateEventComponent} from "./components/create-event/create-event.component";
import {EventComponent} from "./components/event/event.component";
import {LoginComponent} from "./components/login/login.component";
import {SearchComponent} from "./components/search/search.component";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {RegisterComponent} from "./components/register/register.component";

const routes: Routes = [
  {path: '', component: HomepageComponent},
  {path: 'create-event', component: CreateEventComponent},
  {path: 'event', component: EventComponent},
  {path: 'login', component: LoginComponent},
  {path: 'search', component: SearchComponent},
  {path: 'register', component: RegisterComponent},
  {path: '**', component: NotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
