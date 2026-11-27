{
  "startPoint": { "x": 87.0828, "y": 135.5, "headingDeg": -90, "locked": false },
  "lines": [
    {
      "kind": "atomic",
      "id": "intakeSide",
      "name": "intakeSide",
      "endPoint": { "x": 133.5, "y": 135.5 },
      "controlPoints": [],
      "heading": { "type": "linear", "startDeg": -90, "endDeg": 0 },
      "color": "#5B9BD5"
    },
    {
      "kind": "atomic",
      "id": "secondTip1",
      "name": "secondTip1",
      "endPoint": { "x": 86.2047, "y": 22.7081 },
      "controlPoints": [{ "x": 108.1846, "y": 22.8434 }],
      "heading": { "type": "linear", "startDeg": 0, "endDeg": 111.22 },
      "color": "#ED7D31"
    },
    {
      "kind": "atomic",
      "id": "flowerIntake1",
      "name": "flowerIntake1",
      "endPoint": { "x": 94.1991, "y": 11.7584 },
      "controlPoints": [{ "x": 94.1751, "y": 37.4326 }],
      "heading": { "type": "tangential", "reverse": false },
      "color": "#70AD47"
    },
    {
      "kind": "atomic",
      "id": "secondTip2",
      "name": "secondTip2",
      "endPoint": { "x": 86.2047, "y": 22.7081 },
      "controlPoints": [{ "x": 86.1834, "y": 33.7148 }],
      "heading": { "type": "linear", "startDeg": -89.95, "endDeg": 111.22 },
      "color": "#ED7D31"
    },
    {
      "kind": "atomic",
      "id": "flowerIntake2",
      "name": "flowerIntake2",
      "endPoint": { "x": 127.4508, "y": 94.3345 },
      "controlPoints": [
        { "x": 127.8971, "y": 22.83 },
        { "x": 100.6667, "y": 94.2271 }
      ],
      "heading": { "type": "tangential", "reverse": false },
      "color": "#70AD47"
    },
    {
      "kind": "atomic",
      "id": "secondTip3",
      "name": "secondTip3",
      "endPoint": { "x": 85.9899, "y": 22.8591 },
      "controlPoints": [{ "x": 119.9609, "y": 23.1029 }],
      "heading": { "type": "linear", "startDeg": 0.23, "endDeg": 111 },
      "color": "#ED7D31"
    },
    {
      "kind": "atomic",
      "id": "park",
      "name": "park",
      "endPoint": { "x": 130.2623, "y": 31.7346 },
      "controlPoints": [],
      "heading": { "type": "linear", "startDeg": 111, "endDeg": 0 },
      "color": "#A5A5A5"
    }
  ],
  "shapes": [],
  "sequence": [
    { "kind": "wait", "id": "wait-start", "name": "Start delay", "durationMs": 500 },
    { "kind": "wait", "id": "shoot-1", "name": "Shoot", "durationMs": 250 },
    { "kind": "path", "lineId": "intakeSide" },
    { "kind": "wait", "id": "wait-intake", "name": "Intake", "durationMs": 500 },
    { "kind": "path", "lineId": "secondTip1" },
    { "kind": "wait", "id": "shoot-2", "name": "Shoot", "durationMs": 250 },
    { "kind": "path", "lineId": "flowerIntake1" },
    { "kind": "path", "lineId": "secondTip2" },
    { "kind": "wait", "id": "shoot-3", "name": "Shoot", "durationMs": 250 },
    { "kind": "path", "lineId": "flowerIntake2" },
    { "kind": "path", "lineId": "secondTip3" },
    { "kind": "wait", "id": "shoot-4", "name": "Shoot", "durationMs": 250 },
    { "kind": "path", "lineId": "park" }
  ],
  "fieldPoints": [
    { "x": 72.5, "y": 58, "color": "#4d79ff", "radius": 1.5 },
    { "x": 72.5, "y": 83.5, "color": "#4d79ff", "radius": 1.5 }
  ],
  "version": "1.5.0",
  "timestamp": "2026-11-20T00:00:00.000Z"
}
