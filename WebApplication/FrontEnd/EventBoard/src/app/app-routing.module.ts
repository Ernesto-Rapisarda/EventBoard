import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {HomepageComponent} from "./components/homepage/homepage.component";
import {CreateEventComponent} from "./components/create-event/create-event.component";
import {EventComponent} from "./components/event/event.component";
import {LoginComponent} from "./components/login/login.component";
import {SearchComponent} from "./components/search/search.component";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {RegisterComponent} from "./components/register/register.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {AuthGuard} from "./auth/auth.guard";
import {OrganizerComponent} from "./components/organizer/organizer.component";
import {ActivateComponent} from "./components/activate/activate.component";
import {AboutComponent} from "./components/about/about.component";
import {EventEditComponent} from "./components/event-edit/event-edit.component";

const routes: Routes = [
  {path: '', component: HomepageComponent},
  {path: 'profile/activate/:token', component: ActivateComponent},
  {path: 'create-event', component: CreateEventComponent, canActivate: [AuthGuard]},
  {path: 'event/:id', component: EventComponent},
  {path: 'event/edit/:id', component: EventEditComponent},
  {path: 'login', component: LoginComponent, canActivate: [AuthGuard]},
  {path: 'organizer/:id', component: OrganizerComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: 'register', component: RegisterComponent, canActivate: [AuthGuard]},
  {path: 'search', component: SearchComponent},
  {path: 'about', component: AboutComponent},
  {path: '**', component: NotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ onSameUrlNavigation: 'reload' }),],
  exports: [RouterModule]
})
export class AppRoutingModule { }
