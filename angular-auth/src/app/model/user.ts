export interface User {
  id: any;
  name: string;
  username: string;
  role: 'ROLE_USER' | 'ROLE_GERENTE' | 'ROLE_ADMIN';
  active: boolean;
}

export interface UserRegistration {
  name: string;
  username: string;
  password: string;
  role: 'ROLE_USER' | 'ROLE_GERENTE' | 'ROLE_ADMIN';
}
