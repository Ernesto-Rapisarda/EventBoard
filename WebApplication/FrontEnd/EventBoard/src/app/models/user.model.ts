export class User {
  constructor(
    public email: string,
    public name: string,
    public lastName: string,
    public role: string,
    public id: string,
    private _token: string,

  ) {
  }

}
