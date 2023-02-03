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
import {OrganizerComponent} from "./components/organizer/organizer.component";
import {ActivateComponent} from "./components/activate/activate.component";
import {EventEditComponent} from "./components/event-edit/event-edit.component";
import {AdminDashboardComponent} from "./components/admin-dashboard/admin-dashboard.component";

const routes: Routes = [
  {path: '', component: HomepageComponent},
  {path: 'admin/dashboard', component: AdminDashboardComponent},
  {path: 'profile/activate/:token', component: ActivateComponent},
  {path: 'create-event', component: CreateEventComponent},
  {path: 'event/:id', component: EventComponent},
  {path: 'event/edit/:id', component: EventEditComponent},
  {path: 'login', component: LoginComponent},
  {path: 'organizer/:id', component: OrganizerComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'search', component: SearchComponent},
  {path: '**', component: NotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ onSameUrlNavigation: 'reload' }),],
  exports: [RouterModule]
})
export class AppRoutingModule { }
