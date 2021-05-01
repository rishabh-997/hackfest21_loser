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
let heatmap = "http://10.42.0.1:5000/get-image/output_heat_map.png";
let imageURL = null;
let result = null;

const storeUrl = async function (e) {
  imageURL = e.target.value;
};

const store = async function (e) {
  image = e.target.files[0];
  const uploadTask = storage.ref(`brainy/${image.name}`).put(image);
  uploadTask.on(
    "state_changed",
    () => {
      console.log("uploading...");
      document.getElementById("uploadingBrainy").style.display = "inline";
    },
    (error) => {
      console.log(error);
    },
    async () => {
      await storage
        .ref("brainy")
        .child(image.name)
        .getDownloadURL()
        .then((url) => {
          imageURL = url;
          console.log(imageURL);
          document.getElementById("uploadingBrainy").style.display = "none";
          document.getElementById("resultButtonBrainy").style.display =
            "inline";
          document.getElementById("uploadedImageDivisionBrainy").style.display =
            "flex";
          document.getElementById("uploadedImageBrainy").src = imageURL;
        });
    }
  );
};

const getResultByURL = function () {
  console.log("getting results");
  imageURL = document.getElementById("materialUrlBrainy").value;
  var request = require("request");
  const bodyF = `{\n"url": "${imageURL}"\n}`;

  var options = {
    method: "POST",
    url: "http://10.42.0.1:5000/aptos/",
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
    document.getElementById("heatimageBrainy").src = heatmap;
  });
};

const getResult = function () {
  console.log("getting results");
  document.getElementById("uploading2b").style.display = "flex";
  document.getElementById("resulttextb").style.display = "flex";
  var request = require("request");
  const bodyF = `{\n"url": "${imageURL}"\n}`;

  var options = {
    method: "POST",
    url: "http://10.42.0.1:5000/brainy/",
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
    let bold = null;
    if (final <= 20 && final >= 0) {
      bold = document.getElementById("ab");
    } else if (final <= 40 && final > 20) {
      bold = document.getElementById("bb");
    } else if (final <= 60 && final > 40) {
      bold = document.getElementById("cb");
    } else if (final <= 80 && final > 60) {
      bold = document.getElementById("db");
    } else if (final <= 100 && final > 80) {
      bold = document.getElementById("eb");
    } else {
      alert("no output");
      return;
    }

    bold.style.fontWeight = 800;
    bold.style.color = "dodgerblue";
    document.getElementById("resulttextb").style.display = "flex";
    document.getElementById("uploading2b").style.display = "none";
    document.getElementById(
      "resultBrainy"
    ).innerHTML = `Chances for Brain Tumour are ${result}%`;
    document.getElementById("descriptionb").style.display = "flex";
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
        <h2> Brain Tumour Test </h2>
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
                          <div id="uploadingBrainy" style={{ display: "none" }}>
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
                            id="resultButtonBrainy"
                            color="primary"
                            onClick={getResult}
                            style={{ display: "none" }}
                          >
                            Get Results
                          </Button>
                        </p>
                        <div
                          style={{ display: "none", justifyContent: "center" }}
                          id="uploadedImageDivisionBrainy"
                        >
                          <img
                            src="#"
                            id="uploadedImageBrainy"
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
                          id="materialUrlBrainy"
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
                        <div id="uploading2b" style={{ display: "none" }}>
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
                          id="resulttextb"
                          style={{ display: "none", justifyContent: "center" }}
                        >
                          <div
                            id="ab"
                            style={{ marginRight: 10, fontSize: 25 }}
                          >
                            {" "}
                            Safe{" "}
                          </div>
                          <div
                            id="bb"
                            style={{ marginRight: 10, fontSize: 25 }}
                          >
                            {" "}
                            Mild{" "}
                          </div>
                          <div
                            id="cb"
                            style={{ marginRight: 10, fontSize: 25 }}
                          >
                            {" "}
                            Moderate{" "}
                          </div>
                          <div
                            id="db"
                            style={{ marginRight: 10, fontSize: 25 }}
                          >
                            {" "}
                            Severe{" "}
                          </div>
                          <div
                            id="eb"
                            style={{ marginRight: 10, fontSize: 25 }}
                          >
                            {" "}
                            Poliferative{" "}
                          </div>
                        </div>
                        <p>
                          <h2>
                            <div
                              style={{
                                display: "flex",
                                justifyContent: "center",
                              }}
                              id="resultBrainy"
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
                      <div id="descriptionb" style={{ display: "none" }}>
                        <p>
                          BRAIN tUMOUR <br />
                          Dataset -{" "}
                          <a
                            target="_blank"
                            href="https://www.kaggle.com/navoneel/brain-mri-images-for-brain-tumor-detection"
                            rel="noreferrer"
                          >
                            {" "}
                            Brain MRI Images for Brain Tumour Detection{" "}
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
