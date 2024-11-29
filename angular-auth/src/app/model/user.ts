export interface User {
  id: any;
  name: string;
  username: string;
  phone: string;
  role?: 'ROLE_USER'| 'ROLE_ADMIN';
}

export interface UserRegistration {
  name: string;
  username: string;
  password: string;
  phone: string;
  role: 'ROLE_USER' | 'ROLE_ADMIN';
}
