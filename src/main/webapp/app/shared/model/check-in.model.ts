export interface ICheckIn {
    id?: number;
    userID?: number;
    timeCheckIn?: string;
}

export class CheckIn implements ICheckIn {
    constructor(public id?: number, public userID?: number, public timeCheckIn?: string) {}
}
