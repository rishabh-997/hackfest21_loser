# MEDIDOC

## Problem Statement
1. Identifying the right diagnosis for a given person at the earliest is one of the fundamental problems faced by healthcare providers.

2. For certain diseases like Cancer, late diagnosis threatens the chances of survival, chances of healthy lifestyle and also increases treatment costs.

3. Then there are problems such as is a lack of certified medicine practitioners,

4. Affordability of medical expenditures,

5. Significant amount of time taken to diagnose that particular disease. 

6. The physical access to hospitals is still the major barrier to both preventive and curative health services, forming the major differences between the Rural and the Urban India

## MEDIDOC is a project which uses advanced techniques like AI to detect sudden health issues

Presently our application works for the following :

**BREAST CANCER:** Breast cancer is the most frequent cancer among women, impacting 2.1 million women each year, and also causes the greatest number of cancer-related deaths among women.

**DIABETIC RETINOGRAPGY:** Diabetic Retinopathy (DR) is the fastest-growing cause of blindness. There are 415 Million people with diabetes in the world today, and each one is potentially at risk of being diagnosed with DR.

**BRAIN TUMOR:** Processing and analyzing MRI scans to detect and segment the tumour region with performance comparable to an average specialized neurologist..

**SKIN DISEASE:** It is defined as a superfucial growth of the skin that is visually different texture around it. It is difficult to differenciate early melanomas from benign melanocyclic. The extimated 5-year survival rate for patients whose melanoma is detected early is about 98%.

**PNEUNOMIA** : Develop an algorithm that can detect pneumonia from chest X-rays at a level exceeding practicing radiologists. Our model will use frontal-view of X-ray to detect 14 primary diseases using publicly available chest x-ray dataset. Citation: ChexNet

## WHY MEDIDOC? 
 
Need to reduce the diagnosing time taken
The challenge here is that a trained human eye of a medical professional cannot identify subtle signs on these tests.
Low Accessibility and affordability:
The physical access to hospitals is still the major barrier to both preventive and curative health services, and also the major differences between the Rural and the Urban India. Besides, affordability of medical expenditures is one of the biggest problems faced by patients.

## Solution

Our web application using CNNs and ML that facilitates to test and predict the health risk of the patient based on his/her reports for which we have trained the models on FPN (Feature Pyramid Network)- EfficientNet-B0 , Ranger, Mixed Precision Training sets. 
To use our web-application, users have to simply upload their MRI scans depending upon the type of disease or cancer that they want to get diagnosed for. 
After taking the uploaded image as input, the FPN model at backend will generate required heatmaps and predict whether the user has that disease or not. 
If yes, then the model would predict the stage of that disease/cancer with the probabilities. 
The model uses EfficientNet architecture for convolutional neural networks with the state of art techniques to predict results for different scans and inputs.

## Steps To Run the app on local host:

1. Create a virtual environment with the dependencies listed in requirements.txt
2. Make a subfolder and clone rest of the files there
3. Activate the virtual environment with command on root dir : source your_env_name/bin/activate
4. Change the location to subfolder and run app.py using python3 app.py

**USE CASES:**

Our software identifies the key factors/markers of health and fitness of an individual.
The usage and scope of the software is not limited to people,places or time.
The software is intentionally constructed to suit everyone especially the physically disabled and not limit to a sect of society.
