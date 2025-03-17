/** @returns {Promise<import('jest').Config>} */
module.exports = async () => {
    return {
        verbose: true,
    };
};

module.exports = {
    testEnvironment: 'jsdom',
};