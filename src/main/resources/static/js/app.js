document.addEventListener('DOMContentLoaded', function () {

    // ========================================================================
    // LOGIC FOR THE HOME SCREEN TIMER
    // ========================================================================
    const startBtn = document.getElementById('start-session-btn');
    // This entire block only runs if a "start" button is found on the page.
    if (startBtn) {
        // --- State Variables ---
        let timerInterval = null, isPaused = false,
            currentMode = 'work', workDuration = 25, breakDuration = 5;

        // NEW: Variables to track session data for saving
        let workPresetId = null;
        let sessionStartTime = null;
        let breakCounter = 0;

        // --- DOM Elements ---
        const homeScreen = document.getElementById('home-screen');
        const timerWindow = document.getElementById('timer-window');
        const playPauseBtn = document.getElementById('play-pause-btn');
        const stopBtn = document.getElementById('stop-timer-btn');
        const timeDisplay = document.getElementById('time-display');
        const progressIndicator = document.getElementById('progress-indicator');
        const sessionTypeDisplay = document.getElementById('session-type-display');
        const stopModal = document.getElementById('stop-modal-overlay');
        const cancelStopBtn = document.getElementById('cancel-stop-btn');
        const confirmStopBtn = document.getElementById('confirm-stop-btn');

        // --- Timer Functions ---
        function startCountdown() {
            if (timerInterval) clearInterval(timerInterval);
            isPaused = false;
            playPauseBtn.innerHTML = '❚❚';
            timerInterval = setInterval(() => {
                secondsLeft--;
                updateDisplay();
                if (secondsLeft < 0) {
                    switchMode();
                }
            }, 1000);
        }

        function switchMode() {
            clearInterval(timerInterval);
            if (currentMode === 'work') {
                breakCounter++; // Increment break count after a work session
                currentMode = 'break';
            } else {
                currentMode = 'work';
            }
            sessionTypeDisplay.textContent = currentMode.charAt(0).toUpperCase() + currentMode.slice(1);
            secondsLeft = totalSeconds = (currentMode === 'work') ? workDuration * 60 : breakDuration * 60;
            updateDisplay();
            startCountdown();
        }

        // NEW: This function sends the data to the backend API
        function saveSessionAndStop() {
            const sessionData = {
                startTime: sessionStartTime,
                workDuration: workDuration,
                breakDuration: breakDuration,
                breakCounter: breakCounter,
                notes: "", // We can add a notes feature later
                workPresetId: workPresetId
            };

            // The Fetch API call to our backend
            fetch('/api/sessions', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(sessionData),
            })
                .then(response => {
                    if (!response.ok) {
                        console.error('Failed to save session, server responded with:', response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Session saved successfully:', data);
                })
                .catch((error) => {
                    console.error('Error during fetch:', error);
                })
                .finally(() => {
                    // Always stop the UI timer, regardless of save success
                    stopSession();
                });
        }

        function pauseTimer() {
            clearInterval(timerInterval);
            isPaused = true;
            playPauseBtn.innerHTML = '▶';
        }

        function updateDisplay() {
            const minutes = Math.floor(secondsLeft / 60);
            const seconds = secondsLeft % 60;
            timeDisplay.textContent = `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
            const progressPercent = (secondsLeft / totalSeconds) * 100;
            progressIndicator.style.width = `${progressPercent}%`;
        }

        function stopSession() {
            clearInterval(timerInterval);
            timerInterval = null;
            homeScreen.classList.remove('hidden');
            timerWindow.classList.add('hidden');
            if (stopModal) stopModal.classList.add('hidden');
        }

        // --- Timer Event Listeners ---
        startBtn.addEventListener('click', () => {
            workDuration = parseInt(startBtn.dataset.workDuration, 10);
            breakDuration = parseInt(startBtn.dataset.breakDuration, 10);
            workPresetId = parseInt(startBtn.dataset.presetId, 10);

            sessionStartTime = new Date().toISOString();
            breakCounter = 0;

            currentMode = 'work';
            sessionTypeDisplay.textContent = 'Work';
            totalSeconds = workDuration * 60;
            secondsLeft = totalSeconds;
            homeScreen.classList.add('hidden');
            timerWindow.classList.remove('hidden');
            updateDisplay();
            startCountdown();
        });

        if (playPauseBtn) playPauseBtn.addEventListener('click', () => isPaused ? startCountdown() : pauseTimer());

        if (stopBtn) stopBtn.addEventListener('click', () => {
            pauseTimer();
            if (stopModal) stopModal.classList.remove('hidden');
        });

        if (cancelStopBtn) cancelStopBtn.addEventListener('click', () => {
            if (stopModal) stopModal.classList.add('hidden');
            if (!isPaused) startCountdown();
        });

        // MODIFIED: This now calls the function that saves before stopping
        if (confirmStopBtn) confirmStopBtn.addEventListener('click', saveSessionAndStop);
    }


    // ========================================================================
    // LOGIC FOR DELETE MODAL
    // ========================================================================
    const allDeleteTriggers = document.querySelectorAll('.delete-trigger');
    if (allDeleteTriggers.length > 0) {
        const deleteModal = document.getElementById('delete-modal-overlay');
        const confirmDeleteForm = document.getElementById('confirm-delete-form');
        const cancelDeleteBtn = document.getElementById('cancel-delete-btn');

        allDeleteTriggers.forEach(trigger => {
            trigger.addEventListener('click', function (event) {
                event.preventDefault();
                if (deleteModal && confirmDeleteForm) {
                    confirmDeleteForm.action = this.href;
                    deleteModal.classList.remove('hidden');
                }
            });
        });

        if (cancelDeleteBtn) cancelDeleteBtn.addEventListener('click', () => {
            if (deleteModal) deleteModal.classList.add('hidden');
        });

        if (deleteModal) deleteModal.addEventListener('click', function (event) {
            if (event.target === deleteModal) {
                deleteModal.classList.add('hidden');
            }
        });
    }

});