import { IPropertySet } from 'app/shared/model/property-set.model';

export interface IData {
  id?: string;
  revision?: string;
  classificationId?: string;
  propertySets?: IPropertySet[];
  masterId?: string;
}

export const defaultValue: Readonly<IData> = {};
