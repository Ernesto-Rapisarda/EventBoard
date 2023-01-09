export class User {
  constructor(
    public id: string,
    public name: string,
    public lastName: string,
    public username: string,
    public email: string,
    public role: string,
    private _token: string,

  ) {
  }
}
