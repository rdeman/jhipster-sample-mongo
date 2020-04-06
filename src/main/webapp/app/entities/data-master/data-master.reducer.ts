import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDataMaster, defaultValue } from 'app/shared/model/data-master.model';

export const ACTION_TYPES = {
  FETCH_DATAMASTER_LIST: 'dataMaster/FETCH_DATAMASTER_LIST',
  FETCH_DATAMASTER: 'dataMaster/FETCH_DATAMASTER',
  CREATE_DATAMASTER: 'dataMaster/CREATE_DATAMASTER',
  UPDATE_DATAMASTER: 'dataMaster/UPDATE_DATAMASTER',
  DELETE_DATAMASTER: 'dataMaster/DELETE_DATAMASTER',
  RESET: 'dataMaster/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDataMaster>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DataMasterState = Readonly<typeof initialState>;

// Reducer

export default (state: DataMasterState = initialState, action): DataMasterState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DATAMASTER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DATAMASTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DATAMASTER):
    case REQUEST(ACTION_TYPES.UPDATE_DATAMASTER):
    case REQUEST(ACTION_TYPES.DELETE_DATAMASTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DATAMASTER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DATAMASTER):
    case FAILURE(ACTION_TYPES.CREATE_DATAMASTER):
    case FAILURE(ACTION_TYPES.UPDATE_DATAMASTER):
    case FAILURE(ACTION_TYPES.DELETE_DATAMASTER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DATAMASTER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DATAMASTER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DATAMASTER):
    case SUCCESS(ACTION_TYPES.UPDATE_DATAMASTER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DATAMASTER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/data-masters';

// Actions

export const getEntities: ICrudGetAllAction<IDataMaster> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DATAMASTER_LIST,
  payload: axios.get<IDataMaster>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IDataMaster> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DATAMASTER,
    payload: axios.get<IDataMaster>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDataMaster> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DATAMASTER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDataMaster> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DATAMASTER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDataMaster> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DATAMASTER,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
