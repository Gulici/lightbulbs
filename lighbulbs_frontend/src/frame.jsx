import React, { useState } from "react";
import "./style.css";

export const Frame = () => {
  const [selectedWheel, setSelectedWheel] = useState(null);
  const [selectedScene, setSelectedScene] = useState(null);
  const [selectedBrightness, setSelectedBrightness] = useState(null);
  const [selectedTemperature, setSelectedTemperature] = useState(null);
  const [selectedColor, setSelectedColor] = useState(null);
  const [selectedColorType, setSelectedColorType] = useState(null); // 'temperature' or 'hue'
  const [wheelPosition, setWheelPosition] = useState({ x: 621, y: 1017 });
  const [thumbColor, setThumbColor] = useState('#5f3cff');

  const handleWheelClick = (wheelIndex) => {
    setSelectedWheel(wheelIndex);
  };

  const handleSceneClick = (sceneIndex) => {
    setSelectedScene(sceneIndex);
  };
  
  const handleBrightnessClick = (brightnessIndex) => {
    setSelectedBrightness(brightnessIndex);
  };
  
  const handleTemperatureClick = (temperatureIndex) => {
    setSelectedTemperature(temperatureIndex);
    setSelectedColorType('temperature');
    setSelectedColor(null); // Deselect any color when temperature is selected
  };
  
  const handleColorClick = (colorIndex) => {
    setSelectedColor(colorIndex);
    setSelectedColorType('hue');
    setSelectedTemperature(null); // Deselect any temperature when color is selected
    
    // Update thumb color based on the selected color square
    let newColor;
    switch(colorIndex) {
      case 0:
        newColor = '#009bd7'; // Blue
        setWheelPosition({ x: 650, y: 950 });
        break;
      case 1:
        newColor = '#00cdac'; // Teal
        setWheelPosition({ x: 700, y: 900 });
        break;
      case 2:
        newColor = '#0020f3'; // Dark blue
        setWheelPosition({ x: 600, y: 1000 });
        break;
      case 3:
        newColor = '#ff8800'; // Orange
        setWheelPosition({ x: 750, y: 950 });
        break;
      case 4:
        newColor = '#ffcdf2'; // Pink
        setWheelPosition({ x: 700, y: 1050 });
        break;
      case 5:
        newColor = '#ce15b5'; // Purple
        setWheelPosition({ x: 650, y: 1100 });
        break;
      default:
        newColor = '#5f3cff';
    }
    setThumbColor(newColor);
  };
  
  const handleColorWheelClick = (e) => {
    // Get the position relative to the gradient image
    const gradientElement = document.querySelector('.gradient');
    if (gradientElement) {
      const rect = gradientElement.getBoundingClientRect();
      const x = e.clientX - rect.left;
      const y = e.clientY - rect.top;
      
      // Calculate position for the thumb within the gradient boundaries
      const thumbX = Math.min(Math.max(x - 35, 0), rect.width - 71);
      const thumbY = Math.min(Math.max(y - 35, 0), rect.height - 73);
      
      // Update thumb position
      setWheelPosition({
        x: 593 + thumbX,
        y: 855 + thumbY
      });
      
      // Calculate color based on position in the wheel
      // Convert position to polar coordinates (distance from center and angle)
      const centerX = rect.width / 2;
      const centerY = rect.height / 2;
      const deltaX = x - centerX;
      const deltaY = y - centerY;
      
      // Calculate angle (in degrees, 0-360)
      let angle = Math.atan2(deltaY, deltaX) * (180 / Math.PI);
      if (angle < 0) angle += 360;
      
      // Calculate distance from center (0-1)
      const maxDistance = Math.min(centerX, centerY);
      const distance = Math.min(Math.sqrt(deltaX * deltaX + deltaY * deltaY) / maxDistance, 1);
      
      // Convert to HSL
      // Hue is determined by the angle
      const hue = angle;
      // Saturation is determined by the distance from center
      const saturation = distance * 100;
      // Lightness is fixed at 50% for vibrant colors
      const lightness = 50;
      
      const color = `hsl(${hue}, ${saturation}%, ${lightness}%)`;
      setThumbColor(color);
      
      // Deselect any previously selected color square
      setSelectedColor(null);
      
      // Select hue mode
      setSelectedColorType('hue');
      setSelectedTemperature(null);
    }
  };
  
  // Function to handle thumb dragging
  const handleThumbMouseDown = (e) => {
    e.preventDefault();
    
    const startDrag = (e) => {
      const gradientElement = document.querySelector('.gradient');
      if (gradientElement) {
        const rect = gradientElement.getBoundingClientRect();
        const clientX = e.clientX || (e.touches && e.touches[0].clientX);
        const clientY = e.clientY || (e.touches && e.touches[0].clientY);
        
        if (clientX && clientY) {
          // Calculate relative position to the gradient
          const x = clientX - rect.left;
          const y = clientY - rect.top;
          
          // Keep thumb within the gradient boundaries
          const thumbX = Math.min(Math.max(x - 35, 0), rect.width - 71);
          const thumbY = Math.min(Math.max(y - 35, 0), rect.height - 73);
          
          // Update thumb position
          setWheelPosition({
            x: 593 + thumbX,
            y: 855 + thumbY
          });
          
          // Calculate color based on position in the wheel
          // Convert position to polar coordinates (distance from center and angle)
          const centerX = rect.width / 2;
          const centerY = rect.height / 2;
          const deltaX = x - centerX;
          const deltaY = y - centerY;
          
          // Calculate angle (in degrees, 0-360)
          let angle = Math.atan2(deltaY, deltaX) * (180 / Math.PI);
          if (angle < 0) angle += 360;
          
          // Calculate distance from center (0-1)
          const maxDistance = Math.min(centerX, centerY);
          const distance = Math.min(Math.sqrt(deltaX * deltaX + deltaY * deltaY) / maxDistance, 1);
          
          // Convert to HSL
          // Hue is determined by the angle
          const hue = angle;
          // Saturation is determined by the distance from center
          const saturation = distance * 100;
          // Lightness is fixed at 50% for vibrant colors
          const lightness = 50;
          
          const color = `hsl(${hue}, ${saturation}%, ${lightness}%)`;
          setThumbColor(color);
          
          // Deselect any previously selected color square
          setSelectedColor(null);
          
          // Select hue mode
          setSelectedColorType('hue');
          setSelectedTemperature(null);
        }
      }
    };
    
    const stopDrag = () => {
      document.removeEventListener('mousemove', startDrag);
      document.removeEventListener('touchmove', startDrag);
      document.removeEventListener('mouseup', stopDrag);
      document.removeEventListener('touchend', stopDrag);
    };
    
    document.addEventListener('mousemove', startDrag);
    document.addEventListener('touchmove', startDrag);
    document.addEventListener('mouseup', stopDrag);
    document.addEventListener('touchend', stopDrag);
  };

  return (
    <div className="frame" data-model-id="0:3">
      <div className="div">
        <div className="overlap-group">
          <div className="rectangle" />

          <div className="rectangle-2" />

          <div className="rectangle-3" />

          <div className="text-wrapper">MOC ŚWIATŁA/ BRIGHTNESS</div>

          <div className="TEMPERATURA-BARWOWA">
            TEMPERATURA BARWOWA/
            <br />
            COLOR TEMPERATURE
          </div>

          <div className="text-wrapper-2">KOLOR/ HUE</div>

          <div 
            className={`rectangle-4 ${selectedBrightness === 0 ? 'selected-brightness' : ''}`}
            onClick={() => handleBrightnessClick(0)}
          >
            <div className="text-wrapper-3">100%</div>
          </div>

          <div 
            className={`rectangle-15 ${selectedBrightness === 1 ? 'selected-brightness' : ''}`}
            onClick={() => handleBrightnessClick(1)}
          >
            <div className="text-wrapper-4">50%</div>
          </div>

          <div 
            className={`rectangle-6 ${selectedBrightness === 2 ? 'selected-brightness' : ''}`}
            onClick={() => handleBrightnessClick(2)}
          >
            <div className="text-wrapper-5">10%</div>
          </div>

          <div 
            className={`rectangle-7 ${selectedTemperature === 0 ? 'selected-temperature' : ''}`}
            onClick={() => handleTemperatureClick(0)}
          >
            <div className="element-k">
              2200
              <br />K
            </div>
          </div>

          <div 
            className={`rectangle-5 ${selectedTemperature === 1 ? 'selected-temperature' : ''}`}
            onClick={() => handleTemperatureClick(1)}
          >
            <div className="element-k-2">
              4000
              <br />K
            </div>
          </div>

          <div 
            className={`rectangle-14 ${selectedTemperature === 2 ? 'selected-temperature' : ''}`}
            onClick={() => handleTemperatureClick(2)}
          >
            <div className="element-k-3">
              6500
              <br />K
            </div>
          </div>

          <div 
            className={`rectangle-8 ${selectedColor === 0 ? 'selected-color' : ''}`}
            onClick={() => handleColorClick(0)}
          />

          <div 
            className={`rectangle-9 ${selectedColor === 1 ? 'selected-color' : ''}`}
            onClick={() => handleColorClick(1)}
          />

          <div 
            className={`rectangle-10 ${selectedColor === 2 ? 'selected-color' : ''}`}
            onClick={() => handleColorClick(2)}
          />

          <div 
            className={`rectangle-11 ${selectedColor === 3 ? 'selected-color' : ''}`}
            onClick={() => handleColorClick(3)}
          />

          <div 
            className={`rectangle-12 ${selectedColor === 4 ? 'selected-color' : ''}`}
            onClick={() => handleColorClick(4)}
          />

          <div 
            className={`rectangle-13 ${selectedColor === 5 ? 'selected-color' : ''}`}
            onClick={() => handleColorClick(5)}
          />

          <div className="color-wheel-container">
            <img
              className="gradient"
              alt="Gradient"
              src="https://c.animaapp.com/NlgkCxHf/img/gradient@2x.png"
              onClick={handleColorWheelClick}
            />

            <div 
              className="thumb" 
              style={{ 
                left: `${wheelPosition.x}px`, 
                top: `${wheelPosition.y}px`,
                backgroundColor: thumbColor
              }}
              onMouseDown={handleThumbMouseDown}
              onTouchStart={handleThumbMouseDown}
            />
          </div>
        </div>

        <div className="overlap">
          <div 
            className={`ellipse ${selectedWheel === 0 ? 'selected' : ''}`} 
            onClick={() => handleWheelClick(0)}
          />

          <div 
            className={`ellipse-2 ${selectedWheel === 1 ? 'selected' : ''}`} 
            onClick={() => handleWheelClick(1)}
          />

          <div 
            className={`ellipse-3 ${selectedWheel === 2 ? 'selected' : ''}`} 
            onClick={() => handleWheelClick(2)}
          />
        </div>

        <div className="overlap-2">
          <div 
            className={`ellipse ${selectedWheel === 3 ? 'selected' : ''}`} 
            onClick={() => handleWheelClick(3)}
          />

          <div 
            className={`ellipse-3 ${selectedWheel === 4 ? 'selected' : ''}`} 
            onClick={() => handleWheelClick(4)}
          />
        </div>

        <p className="p">WYBIERZ ŻARÓWKĘ/ SELECT LIGHT BULB</p>

        <div className="text-wrapper-6">
          WYBIERZ USTAWIENIA/ SELECT SETTINGS
        </div>

        <div className="text-wrapper-7">SCENY/ SCENES</div>

        <div className="overlap-3">
          <div className="RELAKS-RELAX">
            RELAKS/
            <br />
            RELAX
          </div>

          <div className="PRACA-WORK">
            PRACA/
            <br />
            WORK
          </div>

          <div className="ZABAWA-PLAY">
            ZABAWA/
            <br />
            PLAY
          </div>

          <div className="rectangle-16" />

          <div 
            className={`rectangle-17 ${selectedScene === 0 ? 'selected-scene' : ''}`} 
            onClick={() => handleSceneClick(0)}
          />

          <div 
            className={`rectangle-work ${selectedScene === 1 ? 'selected-scene' : ''}`} 
            onClick={() => handleSceneClick(1)}
          />

          <div 
            className={`rectangle-18 ${selectedScene === 2 ? 'selected-scene' : ''}`} 
            onClick={() => handleSceneClick(2)}
          />

          <div 
            className={`rectangle-play ${selectedScene === 3 ? 'selected-scene' : ''}`} 
            onClick={() => handleSceneClick(3)}
          />
        </div>
      </div>
    </div>
  );
};
