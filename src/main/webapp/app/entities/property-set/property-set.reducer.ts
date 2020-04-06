import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPropertySet, defaultValue } from 'app/shared/model/property-set.model';

export const ACTION_TYPES = {
  FETCH_PROPERTYSET_LIST: 'propertySet/FETCH_PROPERTYSET_LIST',
  FETCH_PROPERTYSET: 'propertySet/FETCH_PROPERTYSET',
  CREATE_PROPERTYSET: 'propertySet/CREATE_PROPERTYSET',
  UPDATE_PROPERTYSET: 'propertySet/UPDATE_PROPERTYSET',
  DELETE_PROPERTYSET: 'propertySet/DELETE_PROPERTYSET',
  RESET: 'propertySet/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPropertySet>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PropertySetState = Readonly<typeof initialState>;

// Reducer

export default (state: PropertySetState = initialState, action): PropertySetState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROPERTYSET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROPERTYSET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROPERTYSET):
    case REQUEST(ACTION_TYPES.UPDATE_PROPERTYSET):
    case REQUEST(ACTION_TYPES.DELETE_PROPERTYSET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROPERTYSET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROPERTYSET):
    case FAILURE(ACTION_TYPES.CREATE_PROPERTYSET):
    case FAILURE(ACTION_TYPES.UPDATE_PROPERTYSET):
    case FAILURE(ACTION_TYPES.DELETE_PROPERTYSET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROPERTYSET_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROPERTYSET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROPERTYSET):
    case SUCCESS(ACTION_TYPES.UPDATE_PROPERTYSET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROPERTYSET):
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

const apiUrl = 'api/property-sets';

// Actions

export const getEntities: ICrudGetAllAction<IPropertySet> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROPERTYSET_LIST,
  payload: axios.get<IPropertySet>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPropertySet> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROPERTYSET,
    payload: axios.get<IPropertySet>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPropertySet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROPERTYSET,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPropertySet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROPERTYSET,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPropertySet> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROPERTYSET,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
