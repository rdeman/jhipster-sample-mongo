import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from './user-management';
// prettier-ignore
import dataMaster, {
  DataMasterState
} from 'app/entities/data-master/data-master.reducer';
// prettier-ignore
import data, {
  DataState
} from 'app/entities/data/data.reducer';
// prettier-ignore
import classification, {
  ClassificationState
} from 'app/entities/classification/classification.reducer';
// prettier-ignore
import propertySet, {
  PropertySetState
} from 'app/entities/property-set/property-set.reducer';
// prettier-ignore
import property, {
  PropertyState
} from 'app/entities/property/property.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly dataMaster: DataMasterState;
  readonly data: DataState;
  readonly classification: ClassificationState;
  readonly propertySet: PropertySetState;
  readonly property: PropertyState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  dataMaster,
  data,
  classification,
  propertySet,
  property,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
