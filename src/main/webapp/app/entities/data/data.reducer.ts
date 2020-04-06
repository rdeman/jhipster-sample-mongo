import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IData, defaultValue } from 'app/shared/model/data.model';

export const ACTION_TYPES = {
  FETCH_DATA_LIST: 'data/FETCH_DATA_LIST',
  FETCH_DATA: 'data/FETCH_DATA',
  CREATE_DATA: 'data/CREATE_DATA',
  UPDATE_DATA: 'data/UPDATE_DATA',
  DELETE_DATA: 'data/DELETE_DATA',
  RESET: 'data/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IData>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DataState = Readonly<typeof initialState>;

// Reducer

export default (state: DataState = initialState, action): DataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DATA):
    case REQUEST(ACTION_TYPES.UPDATE_DATA):
    case REQUEST(ACTION_TYPES.DELETE_DATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DATA):
    case FAILURE(ACTION_TYPES.CREATE_DATA):
    case FAILURE(ACTION_TYPES.UPDATE_DATA):
    case FAILURE(ACTION_TYPES.DELETE_DATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DATA):
    case SUCCESS(ACTION_TYPES.UPDATE_DATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DATA):
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

const apiUrl = 'api/data';

// Actions

export const getEntities: ICrudGetAllAction<IData> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DATA_LIST,
  payload: axios.get<IData>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DATA,
    payload: axios.get<IData>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DATA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DATA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DATA,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
