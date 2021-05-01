import React from "react";
import { makeStyles } from "@material-ui/core/styles";

import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";

import styles from "assets/jss/material-kit-react/views/landingPageSections/teamStyle.js";

import Ridhish from "./Ridhish.js";
import RishabhA from "./RishabhA.js";
import RishabhT from "./RishabhT.js";
import Nainesh from "./Nainesh.js";
import Sanket from "./Sanket";

const useStyles = makeStyles(styles);

export default function TeamSection() {
  const classes = useStyles();
  return (
    <div className={classes.section}>
      <h2 className={classes.title}>Here is our team</h2>
      <div>
        <GridContainer>
          <GridItem xs={12} sm={12} md={4}>
            <Ridhish />
          </GridItem>
          <GridItem xs={12} sm={12} md={4}>
            <RishabhA />
          </GridItem>
          <GridItem xs={12} sm={12} md={4}>
            <RishabhT />
          </GridItem>
          <GridItem xs={12} sm={12} md={4}>
            <Nainesh />
          </GridItem>
          <GridItem xs={12} sm={12} md={4}>
            <Sanket />
          </GridItem>
        </GridContainer>
      </div>
    </div>
  );
}
