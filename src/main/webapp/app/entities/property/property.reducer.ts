import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProperty, defaultValue } from 'app/shared/model/property.model';

export const ACTION_TYPES = {
  FETCH_PROPERTY_LIST: 'property/FETCH_PROPERTY_LIST',
  FETCH_PROPERTY: 'property/FETCH_PROPERTY',
  CREATE_PROPERTY: 'property/CREATE_PROPERTY',
  UPDATE_PROPERTY: 'property/UPDATE_PROPERTY',
  DELETE_PROPERTY: 'property/DELETE_PROPERTY',
  RESET: 'property/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProperty>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PropertyState = Readonly<typeof initialState>;

// Reducer

export default (state: PropertyState = initialState, action): PropertyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROPERTY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROPERTY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROPERTY):
    case REQUEST(ACTION_TYPES.UPDATE_PROPERTY):
    case REQUEST(ACTION_TYPES.DELETE_PROPERTY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROPERTY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROPERTY):
    case FAILURE(ACTION_TYPES.CREATE_PROPERTY):
    case FAILURE(ACTION_TYPES.UPDATE_PROPERTY):
    case FAILURE(ACTION_TYPES.DELETE_PROPERTY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROPERTY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROPERTY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROPERTY):
    case SUCCESS(ACTION_TYPES.UPDATE_PROPERTY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROPERTY):
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

const apiUrl = 'api/properties';

// Actions

export const getEntities: ICrudGetAllAction<IProperty> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROPERTY_LIST,
  payload: axios.get<IProperty>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IProperty> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROPERTY,
    payload: axios.get<IProperty>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProperty> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROPERTY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProperty> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROPERTY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProperty> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROPERTY,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
