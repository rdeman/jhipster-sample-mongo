export interface IProperty {
  id?: string;
  name?: string;
  value?: string;
  propertySetId?: string;
}

export const defaultValue: Readonly<IProperty> = {};
