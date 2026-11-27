{
  "version": "2.0.0",
  "timestamp": "2026-11-24T05:00:00.000Z",
  "startPoint": { "x": 52.6736, "y": 7.0458, "locked": false, "headingDeg": 90 },
  "lines": [
    {
      "id": "intakeSide",
      "color": "#5B9BD5",
      "name": "intakeSide",
      "kind": "atomic",
      "endPoint": { "x": 8, "y": 8 },
      "controlPoints": [{ "x": 27.106, "y": 80.7713 }],
      "heading": { "type": "linear", "startDeg": 90, "endDeg": 270 }
    },
    {
      "id": "intakeSideBack",
      "color": "#9DC3E6",
      "name": "intakeSideBack",
      "kind": "atomic",
      "endPoint": { "x": 8, "y": 14 },
      "controlPoints": [],
      "heading": { "type": "linear", "startDeg": 270, "endDeg": 270 }
    },
    {
      "id": "intakeSideReturn",
      "color": "#9DC3E6",
      "name": "intakeSideReturn",
      "kind": "atomic",
      "endPoint": { "x": 8, "y": 8 },
      "controlPoints": [],
      "heading": { "type": "linear", "startDeg": 270, "endDeg": 270 }
    },
    {
      "id": "shoot2",
      "color": "#ED7D31",
      "name": "shoot2",
      "kind": "atomic",
      "endPoint": { "x": 58.6541, "y": 131.7953 },
      "controlPoints": [
        { "x": 30.4181, "y": 136.6372 },
        { "x": 58.6366, "y": 102.0651 }
      ],
      "heading": { "type": "tangential", "reverse": true }
    },
    {
      "id": "flower1",
      "color": "#70AD47",
      "name": "flower1",
      "kind": "atomic",
      "endPoint": { "x": 46.7574, "y": 123.107 },
      "controlPoints": [{ "x": 46.5461, "y": 98.7601 }],
      "heading": { "type": "tangential", "reverse": false }
    },
    {
      "id": "flower1Back",
      "color": "#A9D18E",
      "name": "flower1Back",
      "kind": "atomic",
      "endPoint": { "x": 46.7574, "y": 117.107 },
      "controlPoints": [],
      "heading": { "type": "linear", "startDeg": 89.5404, "endDeg": 89.5404 }
    },
    {
      "id": "flower1Return",
      "color": "#A9D18E",
      "name": "flower1Return",
      "kind": "atomic",
      "endPoint": { "x": 46.7574, "y": 123.107 },
      "controlPoints": [],
      "heading": { "type": "linear", "startDeg": 89.5404, "endDeg": 89.5404 }
    },
    {
      "id": "shoot3",
      "color": "#ED7D31",
      "name": "shoot3",
      "kind": "atomic",
      "endPoint": { "x": 57.8916, "y": 131.2542 },
      "controlPoints": [{ "x": 57.8275, "y": 100.4671 }],
      "heading": { "type": "tangential", "reverse": true }
    },
    {
      "id": "flower2",
      "color": "#70AD47",
      "name": "flower2",
      "kind": "atomic",
      "endPoint": { "x": 19.2715, "y": 46.2036 },
      "controlPoints": [
        { "x": 21.6455, "y": 117.3189 },
        { "x": 35.8972, "y": 46.0713 }
      ],
      "heading": { "type": "tangential", "reverse": false }
    },
    {
      "id": "flower2Back",
      "color": "#A9D18E",
      "name": "flower2Back",
      "kind": "atomic",
      "endPoint": { "x": 25.2715, "y": 46.2036 },
      "controlPoints": [],
      "heading": { "type": "linear", "startDeg": 179.6326, "endDeg": 179.6326 }
    },
    {
      "id": "flower2Return",
      "color": "#A9D18E",
      "name": "flower2Return",
      "kind": "atomic",
      "endPoint": { "x": 19.2715, "y": 46.2036 },
      "controlPoints": [],
      "heading": { "type": "linear", "startDeg": 179.6326, "endDeg": 179.6326 }
    },
    {
      "id": "shoot4",
      "color": "#ED7D31",
      "name": "shoot4",
      "kind": "atomic",
      "endPoint": { "x": 59.3112, "y": 132.8861 },
      "controlPoints": [
        { "x": 19.6052, "y": 100.1118 },
        { "x": 60.4272, "y": 107.3162 }
      ],
      "heading": { "type": "tangential", "reverse": true }
    },
    {
      "id": "push",
      "color": "#A5A5A5",
      "name": "push",
      "kind": "atomic",
      "endPoint": { "x": 7.22, "y": 68.6731 },
      "controlPoints": [],
      "heading": { "type": "linear", "startDeg": -90, "endDeg": 270 }
    },
    {
      "id": "push2",
      "color": "#A5A5A5",
      "name": "push2",
      "kind": "atomic",
      "endPoint": { "x": 7.22, "y": 102.7686 },
      "controlPoints": [],
      "heading": { "type": "tangential", "reverse": true }
    }
  ],
  "shapes": [],
  "sequence": [
    { "kind": "wait", "id": "wait-start", "name": "Start delay", "durationMs": 3500 },
    { "kind": "wait", "id": "shoot-1", "name": "Shoot", "durationMs": 250 },
    { "kind": "path", "lineId": "intakeSide" },
    { "kind": "path", "lineId": "intakeSideBack" },
    { "kind": "path", "lineId": "intakeSideReturn" },
    { "kind": "path", "lineId": "shoot2" },
    { "kind": "wait", "id": "shoot-2", "name": "Shoot", "durationMs": 250 },
    { "kind": "path", "lineId": "flower1" },
    { "kind": "path", "lineId": "flower1Back" },
    { "kind": "path", "lineId": "flower1Return" },
    { "kind": "path", "lineId": "shoot3" },
    { "kind": "wait", "id": "shoot-3", "name": "Shoot", "durationMs": 250 },
    { "kind": "path", "lineId": "flower2" },
    { "kind": "path", "lineId": "flower2Back" },
    { "kind": "path", "lineId": "flower2Return" },
    { "kind": "path", "lineId": "shoot4" },
    { "kind": "wait", "id": "shoot-4", "name": "Shoot", "durationMs": 250 },
    { "kind": "path", "lineId": "push" },
    { "kind": "path", "lineId": "push2" }
  ],
  "fieldPoints": [
    { "x": 69, "y": 58, "color": "#ff4d4d", "radius": 1.5 },
    { "x": 69, "y": 83.5, "color": "#ff4d4d", "radius": 1.5 }
  ],
  "robot": {
    "forwardVelocity": 75,
    "strafeVelocity": 65,
    "aVelocity": 3.141592653589793,
    "kFriction": 0.1,
    "rLength": 13.727,
    "rWidth": 11.524,
    "safetyMargin": 1,
    "maxAcceleration": 40,
    "maxDeceleration": 40
  },
  "activePaths": []
}
