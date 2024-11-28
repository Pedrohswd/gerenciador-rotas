import { User } from "./user";

export interface Pedido {
    id?: string;
    name?: string;
    description?: string;
    price?: number;
    quantity?: number;
    street?: string;
    city?: string;
    state?: string;
    county?: string;
    status?: string;
    createdAt?: string;
    latitude?: string;
    longitude?: string;
    createdBy: User;
}