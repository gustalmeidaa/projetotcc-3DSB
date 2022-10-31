/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    screens: {
      sm: '640px',
      // => @media (min-width: 640px) { ... }

      md: '768px',
      // => @media (min-width: 768px) { ... }

      ex: '929px',
      // => @media (min-width: 929px) { ... }

      lg: '1024px',
      // => @media (min-width: 1024px) { ... }

      xl: '1280px',
      // => @media (min-width: 1280px) { ... }

      '2xl': '1536px',
      // => @media (min-width: 1536px) { ... }
    }, colors: {
      transparent: 'transparent',

      'black': '#000',
      'little-black': '#343434',
      'white': '#fff',

      brown: {
        700: '#ce9641',
        500: '#eea765',
        300: '#f8c494',
        100: '#faddc2',
      },

      cyan: {
        500: '#81d8f7',
        300: '#9be1fb'
      }
    },
    extend: {
      fontFamily: {
        sans: 'JetBrains Mono, sans-serif'
      }
    },
  },
  plugins: [],
}
