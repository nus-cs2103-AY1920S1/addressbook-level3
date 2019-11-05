# DEMO

## Objective

1. Assume you are giving a demo to a higher-level manager of your company
2. Showcase how the feature improves the user’s life

## Context

1. Insurelytics has data points of person, policies, and their associations.

## Significance

1. How can we leverage these data points to produce insights for the agent?

## Command

1. Explain each indicator's purpose
   1. `display i/gender-breakdown f/piechart`
   1. `display i/policy-popularity-breakdown f/piechart`
   1. `SeniorCare` has very low take up rate
   1. `display i/age-group-breakdown f/piechart`
   1. Low takeup rate amongst elderly contacts
2. Explain the different visual representation
   2. `display i/age-group-breakdown f/barchart`
   1. `display i/age-group-breakdown f/linechart`

## Extension

1. `display` can be extended to include other indicators such as `contact-list-growth-rate` or `gender-breakdown-by-policy`
2. Larger idea is to leverage on data points to produce insights for the agent. These insights allow the agent to make data
   driven decisions.
