export class Category {
    tag: string;
    label: string;

    constructor() {
        if (this.tag === undefined) this.tag = null;
        if (this.label === undefined) this.label = null;
    }
}