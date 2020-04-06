import { DataType } from 'app/shared/model/enumerations/data-type.model';

export interface IDataMaster {
  id?: string;
  name?: string;
  type?: DataType;
}

export const defaultValue: Readonly<IDataMaster> = {};
