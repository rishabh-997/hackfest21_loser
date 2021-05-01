import React from "react";
import { storage } from "../firebase";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import InputAdornment from "@material-ui/core/InputAdornment";
import CircularProgress from "@material-ui/core/CircularProgress";

import ImageIcon from "@material-ui/icons/Image";
import LinkIcon from "@material-ui/icons/Link";
import DescriptionIcon from "@material-ui/icons/Description";
import BrokenImageIcon from "@material-ui/icons/BrokenImage";

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
  const uploadTask = storage.ref(`corona/${image.name}`).put(image);
  uploadTask.on(
    "state_changed",
    () => {
      console.log("uploading...");
      document.getElementById("uploading").style.display = "inline";
    },
    (error) => {
      console.log(error);
    },
    async () => {
      await storage
        .ref("corona")
        .child(image.name)
        .getDownloadURL()
        .then((url) => {
          imageURL = url;
          console.log(imageURL);
          document.getElementById("uploading").style.display = "none";
          document.getElementById("resultButton").style.display = "inline";
          document.getElementById("uploadedImageDivision").style.display =
            "flex";
          document.getElementById("uploadedImage").src = imageURL;
        });
    }
  );
};

const getResultByURL = function () {
  console.log("getting results");
  imageURL = document.getElementById("materialUrl").value;
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
    document.getElementById("result").innerHTML = result;
    document.getElementById("heatimage").src = heatmap;
  });
};

const getResult = function () {
  console.log("getting results");
  document.getElementById("uploading2").style.display = "flex";
  document.getElementById("resulttext").style.display = "flex";
  var request = require("request");

  var options = {
    method: "POST",
    url: "http://34.71.122.83:8080/corona/",
    headers: {
      "Content-Type": ["application/json", "text/plain"],
      "Access-Control-Allow-Origin": "no-cors",
    },
    body:
      '{\n\t"url": "https://firebasestorage.googleapis.com/v0/b/innerve-hackathon.appspot.com/o/corona%2Fcropped-1920-1080-686433.jpg?alt=media&token=9f6b447b-d1a7-4409-8ba2-e3f43ad6c51b"\n}',
  };
  request(options, function (error, response) {
    if (error) throw new Error(error);
    console.log(response.body);
  });

  /*
  var options = {
    method: "POST",
    url: "http://34.71.122.83:8080/corona/",
    headers: {
      "Content-Type": "application/json,application/json"
    },
    body: bodyF
  };

  request(options, function(error, response, body) {
    if (error) throw new Error(error);

    console.log(body);
    result = body.slice(14, body.length - 2);
    const final = Number(result);
    console.log(final);
    let bold = null;
    if (final <= 20 && final >= 0) {
      bold = document.getElementById("a");
    } else if (final <= 40 && final > 20) {
      bold = document.getElementById("b");
    } else if (final <= 60 && final > 40) {
      bold = document.getElementById("c");
    } else if (final <= 80 && final > 60) {
      bold = document.getElementById("d");
    } else if (final <= 100 && final > 80) {
      bold = document.getElementById("e");
    } else {
      alert("no output");
      return;
    }

    bold.style.fontWeight = 800;
    bold.style.color = "dodgerblue";
    document.getElementById("resulttext").style.display = "flex";
    document.getElementById("uploading2").style.display = "none";
    document.getElementById(
      "result"
    ).innerHTML = `The disease is ${result}% severe`;
    document.getElementById("heatimage").style.display = "flex";
    document.getElementById("heatimage").src = heatmap;
    document.getElementById("description").style.display = "flex";
  });
  */
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
    <div
      className={classes.section}
      style={{ paddingBottom: 0, marginBottom: 0 }}
    >
      <div style={heading}>
        <h2> Chest X-ray </h2>
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
                          <div id="uploading" style={{ display: "none" }}>
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
                            id="resultButton"
                            color="primary"
                            onClick={getResult}
                            style={{ display: "none" }}
                          >
                            Get Results
                          </Button>
                        </p>
                        <div
                          style={{ display: "none", justifyContent: "center" }}
                          id="uploadedImageDivision"
                        >
                          <img
                            src="#"
                            id="uploadedImage"
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
                          id="materialUrl"
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
                        <div id="uploading2" style={{ display: "none" }}>
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
                          id="resulttext"
                          style={{ display: "none", justifyContent: "center" }}
                        >
                          <div id="a" style={{ marginRight: 10, fontSize: 25 }}>
                            {" "}
                            Safe{" "}
                          </div>
                          <div id="b" style={{ marginRight: 10, fontSize: 25 }}>
                            {" "}
                            Mild{" "}
                          </div>
                          <div id="c" style={{ marginRight: 10, fontSize: 25 }}>
                            {" "}
                            Moderate{" "}
                          </div>
                          <div id="d" style={{ marginRight: 10, fontSize: 25 }}>
                            {" "}
                            Severe{" "}
                          </div>
                          <div id="e" style={{ marginRight: 10, fontSize: 25 }}>
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
                              id="result"
                            >
                              {" "}
                            </div>
                          </h2>
                        </p>
                      </span>
                    ),
                  },
                  {
                    tabButton: "Heat-Map",
                    tabIcon: BrokenImageIcon,
                    tabContent: (
                      <span>
                        <p>
                          <img
                            src="#"
                            id="heatimage"
                            style={{ display: "none" }}
                          />
                        </p>
                      </span>
                    ),
                  },
                  {
                    tabButton: "Description",
                    tabIcon: DescriptionIcon,
                    tabContent: (
                      <div id="description" style={{ display: "none" }}>
                        <p>
                          APTOS <br />
                          Dataset -{" "}
                          <a
                            target="_blank"
                            href="http://academictorrents.com/details/d8653db45e7f111dc2c1b595bdac7ccf695efcfd"
                            rel="noreferrer"
                          >
                            {" "}
                            2019 diabetic retinopathy dataset{" "}
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
                          Accuracy - 79.07% ( 5-ary classification ) <br />
                          <a
                            target="_blank"
                            href="https://en.wikipedia.org/wiki/Cohen%27s_kappa"
                            rel="noreferrer"
                          >
                            {" "}
                            Kappa ( qwk ){" "}
                          </a>{" "}
                          - 0.902
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
