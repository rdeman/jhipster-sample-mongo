import { IProperty } from 'app/shared/model/property.model';

export interface IPropertySet {
  id?: string;
  name?: string;
  properties?: IProperty[];
  dataId?: string;
}

export const defaultValue: Readonly<IPropertySet> = {};
