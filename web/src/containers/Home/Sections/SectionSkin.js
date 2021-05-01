import React from "react";
import { storage } from "../firebase";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import InputAdornment from "@material-ui/core/InputAdornment";
import CircularProgress from "@material-ui/core/CircularProgress";

// @material-ui/icons
import ImageIcon from "@material-ui/icons/Image";
import LinkIcon from "@material-ui/icons/Link";
import DescriptionIcon from "@material-ui/icons/Description";

// core components
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import CustomTabs from "components/CustomTabs/CustomTabs.js";
import NavPills from "components/NavPills/NavPills.js";
import CustomInput from "components/CustomInput/CustomInput.js";
import Button from "components/CustomButtons/Button.js";

import styles from "assets/jss/material-kit-react/views/componentsSections/tabsStyle.js";

const useStyles = makeStyles(styles);

let image = null;
let imageURL = null;
let result = null;

const storeUrl = async function (e) {
  imageURL = e.target.value;
};

const store = async function (e) {
  image = e.target.files[0];
  const uploadTask = storage.ref(`skinny/${image.name}`).put(image);
  uploadTask.on(
    "state_changed",
    () => {
      console.log("uploading...");
      document.getElementById("uploadingSkinny").style.display = "inline";
    },
    (error) => {
      console.log(error);
    },
    async () => {
      await storage
        .ref("skinny")
        .child(image.name)
        .getDownloadURL()
        .then((url) => {
          imageURL = url;
          console.log(imageURL);
          document.getElementById("uploadingSkinny").style.display = "none";
          document.getElementById("resultButtonSkinny").style.display =
            "inline";
          document.getElementById("uploadedImageDivisionSkinny").style.display =
            "flex";
          document.getElementById("uploadedImageSkinny").src = imageURL;
        });
    }
  );
};

const getResultByURL = function () {
  console.log("getting results");
  imageURL = document.getElementById("materialUrlSkinny").value;
  var request = require("request");
  const bodyF = `{\n"url": "${imageURL}"\n}`;

  var options = {
    method: "POST",
    url: "http://10.42.0.1:5000/skinny/",
    headers: {
      Host: "10.42.0.1:5000",
      Accept: "application/json",
      "Content-Type": "application/json,application/json",
    },
    body: bodyF,
  };

  request(options, function (error, response, body) {
    if (error) throw new Error(error);

    console.log(body);
    result = body.slice(14, body.length - 2);
    console.log(result);
  });
};

const getResult = function () {
  console.log("getting results");
  document.getElementById("uploading2s").style.display = "flex";
  document.getElementById("resulttexts").style.display = "flex";
  var request = require("request");
  const bodyF = `{\n"url": "${imageURL}"\n}`;

  var options = {
    method: "POST",
    url: "http://10.42.0.1:5000/skinny/",
    headers: {
      Host: "10.42.0.1:5000",
      Accept: "application/json",
      "Content-Type": "application/json,application/json",
    },
    body: bodyF,
  };

  request(options, function (error, response, body) {
    if (error) throw new Error(error);

    console.log(body);
    result = body.slice(14, body.length - 2);
    const final = Number(result);
    console.log(final);
    let bold = document.getElementById("resulttexts");
    if (final === 0) {
      bold.innerHTML = `Acitinic Keratosis`;
    } else if (final === 1) {
      bold.innerHTML = `Basal Cell Carcinoma`;
    } else if (final === 2) {
      bold.innerHTML = `Benign Keratosis`;
    } else if (final === 3) {
      bold.innerHTML = `Dermatofibroma`;
    } else if (final === 4) {
      bold.innerHTML = `Melanome`;
    } else if (final === 5) {
      bold.innerHTML = `Melanocytic nevi`;
    } else if (final === 6) {
      bold.innerHTML = `Vascular Keratosis`;
    } else {
      alert("Invalid image");
      return;
    }

    bold.style.fontWeight = 800;
    bold.style.color = "dodgerblue";
    bold.style.display = "flex";
    document.getElementById("pretext").style.display = "flex";
    document.getElementById("uploading2s").style.display = "none";
    document.getElementById("descriptions").style.display = "flex";
  });
};

const heading = {
  display: "flex",
  justifyContent: "center",
  marginBottom: "3%",
};

export default function SectionTabs() {
  const classes = useStyles();
  let img = null;
  return (
    <div className={classes.section} style={{ padding: 0, margin: 0 }}>
      <div style={heading}>
        <h2> Skin Cancer Test </h2>
      </div>
      <div className={classes.container}>
        <div id="nav-tabs">
          <GridContainer>
            <GridItem xs={12} sm={12} md={6}>
              <CustomTabs
                headerColor="primary"
                tabs={[
                  {
                    tabName: "Upload Image",
                    tabIcon: ImageIcon,
                    tabContent: (
                      <div>
                        <p className={classes.textCenter}>
                          <input
                            type="file"
                            alt="upload file image"
                            onChange={store}
                          />
                          <div id="uploadingSkinny" style={{ display: "none" }}>
                            {" "}
                            <div
                              style={{
                                display: "flex",
                                justifyContent: "center",
                              }}
                            >
                              <CircularProgress />{" "}
                            </div>
                          </div>
                          <Button
                            id="resultButtonSkinny"
                            color="primary"
                            onClick={getResult}
                            style={{ display: "none" }}
                          >
                            Get Results
                          </Button>
                        </p>
                        <div
                          style={{ display: "none", justifyContent: "center" }}
                          id="uploadedImageDivisionSkinny"
                        >
                          <img
                            src="#"
                            id="uploadedImageSkinny"
                            height="300px"
                            width="300px"
                          />
                        </div>
                      </div>
                    ),
                  },
                  {
                    tabName: "Upload Image Url",
                    tabIcon: LinkIcon,
                    tabContent: (
                      <p className={classes.textCenter}>
                        <CustomInput
                          labelText="Upload url here"
                          id="materialUrlSkinny"
                          type="text"
                          onChange={storeUrl}
                          formControlProps={{
                            fullWidth: true,
                          }}
                          inputProps={{
                            endAdornment: (
                              <InputAdornment position="end">
                                <LinkIcon />
                              </InputAdornment>
                            ),
                          }}
                        />
                        <Button onClick={getResultByURL} color="primary">
                          Get Results
                        </Button>
                      </p>
                    ),
                  },
                ]}
              />
            </GridItem>

            <GridItem xs={12} sm={12} md={8} lg={6}>
              <NavPills
                color="primary"
                tabs={[
                  {
                    tabButton: "Results",
                    tabIcon: LinkIcon,
                    tabContent: (
                      <span>
                        <div id="uploading2s" style={{ display: "none" }}>
                          {" "}
                          <div
                            style={{
                              display: "flex",
                              justifyContent: "center",
                            }}
                          >
                            <CircularProgress />{" "}
                          </div>
                        </div>
                        <br />
                        <div
                          id="pretext"
                          style={{
                            display: "none",
                            justifyContent: "center",
                            fontSize: 20,
                          }}
                        >
                          Type of Skin Lesion:
                        </div>{" "}
                        <br />
                        <div
                          id="resulttexts"
                          style={{
                            display: "none",
                            justifyContent: "center",
                            fontSize: 25,
                          }}
                        ></div>
                        <p>
                          <h2>
                            <div
                              style={{
                                display: "flex",
                                justifyContent: "center",
                              }}
                              id="resultSkinny"
                            >
                              {" "}
                            </div>
                          </h2>
                        </p>
                      </span>
                    ),
                  },
                  {
                    tabButton: "Description",
                    tabIcon: DescriptionIcon,
                    tabContent: (
                      <div id="descriptions" style={{ display: "none" }}>
                        <p>
                          SKIN CANCER <br />
                          Dataset -{" "}
                          <a
                            target="_blank"
                            href="https://dataverse.harvard.edu/dataset.xhtml?persistentId=doi:10.7910/DVN/DBW86T"
                            rel="noreferrer"
                          >
                            {" "}
                            The HAM 10000 dataset{" "}
                          </a>{" "}
                          <br />
                          Base Model Architecture -{" "}
                          <a
                            target="_blank"
                            href="https://arxiv.org/abs/1905.11946"
                            rel="noreferrer"
                          >
                            {" "}
                            EfficientNet-b0{" "}
                          </a>{" "}
                          <br />
                          Optimizer -{" "}
                          <a
                            target="_blank"
                            href="https://github.com/lessw2020/Ranger-Deep-Learning-Optimizer"
                            rel="noreferrer"
                          >
                            {" "}
                            Ranger{" "}
                          </a>{" "}
                          <br />
                          PERFORMANCE <br />
                          Accuracy - 87.15% <br />
                          Recall - 0.71 <br />
                          F1 Score - 0.75 <br />
                          Kappa ( qwk ) - 90.42 <br />
                        </p>
                      </div>
                    ),
                  },
                ]}
              />
            </GridItem>
          </GridContainer>
        </div>
      </div>
    </div>
  );
}
