import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/data-master">
      <Translate contentKey="global.menu.entities.dataMaster" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/data">
      <Translate contentKey="global.menu.entities.data" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/classification">
      <Translate contentKey="global.menu.entities.classification" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/property-set">
      <Translate contentKey="global.menu.entities.propertySet" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/property">
      <Translate contentKey="global.menu.entities.property" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
