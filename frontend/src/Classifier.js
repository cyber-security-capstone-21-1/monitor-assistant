import React, { useState, useRef, useReducer } from "react";
import * as mobilenet from "@tensorflow-models/mobilenet";
import image from './assets/images/dog1.jpg';

const machine = {
    initial: "initial",
    states: {
      initial: { on: { next: "loadingModel" }, showImage: true },
      loadingModel: { on: { next: "modelReady" }, showImage: true },
      modelReady: { on: { next: "imageReady" }, showImage: true },
      imageReady: { on: { next: "identifying" }, showImage: true },
      identifying: { on: { next: "complete" } },
      complete: {
        on: { next: "modelReady" },
        showImage: true,
        showResults: true,
      },
    },
};

function Classifier () {
    const [results, setResults] = useState([]);
    const [imageURL, setImageURL] = useState(image);
    const [model, setModel] = useState(null);
    const imageRef = useRef();
    const inputRef = useRef();
  
    const reducer = (state, event) =>
      machine.states[state].on[event] || machine.initial;
  
    const [appState, dispatch] = useReducer(reducer, machine.initial);
    const next = () => dispatch("next");
  
    const loadModel = async () => {
      next();
      const model = await mobilenet.load();
      setModel(model);
      next();
  
      //Skip Upload image step=
      next();
      //=======================
  
      //identify code==========
      next();
      const results = await model.classify(imageRef.current);
      setResults(results);
      next();
      //=======================
    };
  
    const identify = async () => {
      next();
      const results = await model.classify(imageRef.current);
      setResults(results);
      next();
    };
  
    const reset = async () => {
      setResults([]);
      next();
    };
  
    const upload = () => inputRef.current.click();
    const handleUpload = (event) => {
      const { files } = event.target;
      console.log("업로드 함수 실행");
      console.log(files);
      if (files.length > 0) {
        const url = URL.createObjectURL(event.target.files[0]);
        console.log("url ", url);
        setImageURL(url);
        next();
      }
    };
  
    const actionButton = {
      initial: { action: loadModel, text: "Predict it!" },
      loadingModel: { text: "Loading Model..." },
      modelReady: { action: upload, text: "Upload Image" },
      imageReady: { action: identify, text: "Predict it!" },
      identifying: { text: "Wait a sec..." },
      complete: { action: reset, text: "Reset" },
    };
  
    const { showImage, showResults } = machine.states[appState];
  
    return (
      <div>
        {<img src={imageURL} ref={imageRef}></img>}
        <input
          type="file"
          accept="image/*"
          capture="camera"
          onChange={handleUpload}
          ref={inputRef}
        ></input>
        {showResults && (
          <ul>
            {results.map(({ className, probability }) => (
              <li key={className}>{`${className} is ${probability * 100}%`}</li>
            ))}
          </ul>
        )}
        <button onClick={actionButton[appState].action || (() => {})}>
          {actionButton[appState].text}
        </button>
      </div>
    );
}

export default Classifier;